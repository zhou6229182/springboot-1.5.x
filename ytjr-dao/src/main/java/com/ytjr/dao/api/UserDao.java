package com.ytjr.dao.api;

import com.ytjr.entity.api.RoleEntity;
import com.ytjr.entity.api.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    UserEntity getUserByUsername(@Param("username") String username);

    List<RoleEntity> getRolesByUserId(@Param("userId") String userId);

}
