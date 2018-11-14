package com.ytjr.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.ytjr.dao.api.UserDao;
import com.ytjr.entity.api.UserEntity;
import com.ytjr.iservice.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity queryObject() {
        return userDao.queryObject();
    }

    @Override
    @Cacheable(value = "user", key = "'userlist'")
    public List<UserEntity> queryList() {
        PageHelper.startPage(1, 2);
        return userDao.queryList();
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public void deleteCache() {
    }

    @Override
    @Cacheable(value = "time", key = "'nowtime'")
    public Date nowTime() {
        return new Date();
    }
}
