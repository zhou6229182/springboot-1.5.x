package com.ytjr.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ytjr.api.utils.R;
import com.ytjr.iservice.api.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class TestController {

    @Reference(url = "127.0.0.1:20880")
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
}
