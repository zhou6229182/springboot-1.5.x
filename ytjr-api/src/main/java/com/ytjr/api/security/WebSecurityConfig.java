package com.ytjr.api.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytjr.api.utils.R;
import com.ytjr.common.enums.ResponseEnums;
import com.ytjr.entity.api.UserEntity;
import com.ytjr.iservice.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.io.PrintWriter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Reference
    private IUserService userService;

    private UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;

    private UrlAccessDecisionManager urlAccessDecisionManager;

    private AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

    private JsonLoginUrlAuthenticationEntryPoint jsonLoginUrlAuthenticationEntryPoint;

    private JsonSessionExpiredStrategy jsonSessionExpiredStrategy;

    private ObjectMapper om;

    @Autowired
    public WebSecurityConfig(UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource, UrlAccessDecisionManager urlAccessDecisionManager, AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler, JsonLoginUrlAuthenticationEntryPoint jsonLoginUrlAuthenticationEntryPoint, JsonSessionExpiredStrategy jsonSessionExpiredStrategy, ObjectMapper om) {
        this.urlFilterInvocationSecurityMetadataSource = urlFilterInvocationSecurityMetadataSource;
        this.urlAccessDecisionManager = urlAccessDecisionManager;
        this.authenticationAccessDeniedHandler = authenticationAccessDeniedHandler;
        this.jsonLoginUrlAuthenticationEntryPoint = jsonLoginUrlAuthenticationEntryPoint;
        this.jsonSessionExpiredStrategy = jsonSessionExpiredStrategy;
        this.om = om;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { //密码加密
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/login.html", "/index.html", "/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                        o.setAccessDecisionManager(urlAccessDecisionManager);
                        return o;
                    }
                })
                .and()
                .formLogin().loginPage("/login.html").loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password")
                .permitAll()
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    PrintWriter out = httpServletResponse.getWriter();
                    if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                        out.write(om.writeValueAsString(R.error(ResponseEnums.INVALID_USER_OR_PASSWORD)));
                    } else if (e instanceof DisabledException) {
                        out.write(om.writeValueAsString(R.error(ResponseEnums.USER_DISABLED)));
                    } else {
                        out.write(om.writeValueAsString(R.error(ResponseEnums.LOGIN_FAILED)));
                    }
                    out.flush();
                    out.close();
                })
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    PrintWriter out = httpServletResponse.getWriter();
                    UserEntity user = (UserEntity) authentication.getPrincipal();
                    String s = om.writeValueAsString(R.ok().put("user", user));
                    out.write(s);
                    out.flush();
                    out.close();
                })
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jsonLoginUrlAuthenticationEntryPoint).accessDeniedHandler(authenticationAccessDeniedHandler)
                .and()
                .sessionManagement()
                //-1一个账户能登录多次(使用UserDetails的equals和hashCode方法判断是否为同一用户)
                .maximumSessions(-1)
                //session超出数量时的动作 true代表限制后来的登录，登录时会报SessionAuthenticationException错误 false代表强制之前退出
                //.maxSessionsPreventsLogin(true)
                .sessionRegistry(sessionRegistry())
                //session过期事件管理
                .expiredSessionStrategy(jsonSessionExpiredStrategy);
    }
}
