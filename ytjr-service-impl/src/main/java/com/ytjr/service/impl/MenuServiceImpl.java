package com.ytjr.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ytjr.dao.api.MenuDao;
import com.ytjr.entity.api.MenuEntity;
import com.ytjr.iservice.api.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<MenuEntity> getAllMenu() {
        return menuDao.getAllMenu();
    }
}
