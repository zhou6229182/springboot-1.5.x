package com.ytjr.api.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ytjr.entity.api.MenuEntity;
import com.ytjr.entity.api.RoleEntity;
import com.ytjr.iservice.api.IMenuService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Reference
    private IMenuService menuService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        List<MenuEntity> allMenu = menuService.getAllMenu();
        for (MenuEntity menu : allMenu) {
            if (antPathMatcher.match(menu.getUrl(), requestUrl) && menu.getRoles().size() > 0) {
                List<RoleEntity> roles = menu.getRoles();
                int size = roles.size();
                String[] values = new String[size];
                for (int i = 0; i < size; i++) {
                    values[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(values);
            }
        }
        //返回ROLE_LOGIN，没有匹配上的资源，都是登录访问（返回null则不需要权限，返回ROLE_ANONYMOUS需要匿名权限）
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
