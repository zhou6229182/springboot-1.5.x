package com.ytjr.dao.api;

import com.ytjr.entity.api.UserEntity;

import java.util.List;

public interface UserDao {

    UserEntity queryObject();

    List<UserEntity> queryList();
}
