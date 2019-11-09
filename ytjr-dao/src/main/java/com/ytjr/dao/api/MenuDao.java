package com.ytjr.dao.api;

import com.ytjr.entity.api.MenuEntity;
import com.ytjr.entity.api.RoleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao {

    List<MenuEntity> getAllMenu();

    List<RoleEntity> getRolesByMenuId(@Param("menuId") String menuId);
}
