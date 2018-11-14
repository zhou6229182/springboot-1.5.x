package com.ytjr.iservice.api;

import com.ytjr.entity.api.UserEntity;

import java.util.Date;
import java.util.List;

public interface IUserService {
    UserEntity queryObject();

    List<UserEntity> queryList();

    void deleteCache();

    Date nowTime();
}
