package com.ytjr.service.impl;

import com.ytjr.entity.api.UserEntity;
import com.ytjr.iservice.api.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserTest {

    @Resource
    private IUserService userService;

    @Test
    public void test() {
        System.out.println(userService.queryObject().getName());
    }

    @Test
    public void testList() {
        List<UserEntity> list = userService.queryList();
        list.forEach(System.out::println);
    }
}
