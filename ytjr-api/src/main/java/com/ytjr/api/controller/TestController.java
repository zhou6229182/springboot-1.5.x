package com.ytjr.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ytjr.api.utils.R;
import com.ytjr.entity.api.UserEntity;
import com.ytjr.iservice.api.IUserService;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class TestController {

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
    public UserEntity name(HttpSession session) {
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        return (UserEntity) securityContextImpl.getAuthentication().getPrincipal();
    }
}
