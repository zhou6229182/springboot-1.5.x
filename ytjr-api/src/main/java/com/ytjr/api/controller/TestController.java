package com.ytjr.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ytjr.api.utils.R;
import com.ytjr.entity.api.UserEntity;
import com.ytjr.iservice.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class TestController {

    private SessionRegistry sessionRegistry;

    @Autowired
    public TestController(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @Reference
    private IUserService userService;

    @GetMapping("/delete")
    public R delete() {
        userService.deleteCache();
        return R.ok();
    }

    @GetMapping("/nowtime")
    public Date nowTime() {
        return userService.nowTime();
    }

    @GetMapping("/name")
    public UserEntity name(Authentication authentication, HttpSession session) {
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        //return (UserEntity) authentication.getPrincipal();//获取当前登录用户的三种方式
        //return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (UserEntity) securityContextImpl.getAuthentication().getPrincipal();
    }

    // 根据用户清除session
    @GetMapping("/removeUserSessionByUsername")
    public R removeUserSessionByUsername(String username) {
        List<Object> users = sessionRegistry.getAllPrincipals(); // 获取session中所有的用户信息
        for (Object principal : users) {
            if (principal instanceof UserEntity) {
                UserEntity loggedUser = (UserEntity) principal;
                if (username.equals(loggedUser.getUsername())) {
                    List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false); // false代表不包含过期session
                    if (null != sessionsInfo && sessionsInfo.size() > 0) {
                        for (SessionInformation sessionInformation : sessionsInfo) {
                            sessionInformation.expireNow();
                        }
                    }
                }
            }
        }
        return R.ok();
        /*SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
        if (sessionInformation != null && !sessionInformation.isExpired()) {
            sessionInformation.expireNow();
        }
        return R.ok();*/
    }
}
