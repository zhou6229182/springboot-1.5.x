package com.ytjr.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ytjr.entity.api.UserEntity;
import com.ytjr.iservice.api.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class TestController {

    @Reference(url = "127.0.0.1:20880")
    private IUserService userService;

    @GetMapping("/name")
    public List<UserEntity> getList() {
        return userService.queryList();
    }

    @GetMapping("/delete")
    public String delete() {
        userService.deleteCache();
        return "result";
    }

    @GetMapping("/nowtime")
    public Date nowTime() {
        return userService.nowTime();
    }
}
