package com.ytjr.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.ytjr.dao.api.UserDao;
import com.ytjr.entity.api.UserEntity;
import com.ytjr.iservice.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    @CacheEvict(value = "time", allEntries = true)
    public void deleteCache() {
    }

    @Override
    @Cacheable(value = "time", key = "'nowtime'")
    public Date nowTime() {
        return new Date();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userDao.getUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不正确");
        }
        return user;
    }
}
