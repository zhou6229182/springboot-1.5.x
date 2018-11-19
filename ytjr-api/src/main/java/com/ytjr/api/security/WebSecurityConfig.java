package com.ytjr.api.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytjr.api.utils.R;
import com.ytjr.api.utils.UserUtils;
import com.ytjr.common.enums.ResponseEnums;
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

    private ObjectMapper om;

    @Autowired
    public WebSecurityConfig(UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource, UrlAccessDecisionManager urlAccessDecisionManager, AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler, JsonLoginUrlAuthenticationEntryPoint jsonLoginUrlAuthenticationEntryPoint, ObjectMapper om) {
        this.urlFilterInvocationSecurityMetadataSource = urlFilterInvocationSecurityMetadataSource;
        this.urlAccessDecisionManager = urlAccessDecisionManager;
        this.authenticationAccessDeniedHandler = authenticationAccessDeniedHandler;
        this.jsonLoginUrlAuthenticationEntryPoint = jsonLoginUrlAuthenticationEntryPoint;
        this.om = om;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { //密码加密
        return new BCryptPasswordEncoder();
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
                    String s = om.writeValueAsString(R.ok().put("user", UserUtils.getCurrentUser()));
                    out.write(s);
                    out.flush();
                    out.close();
                })
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jsonLoginUrlAuthenticationEntryPoint).accessDeniedHandler(authenticationAccessDeniedHandler);
    }
}
