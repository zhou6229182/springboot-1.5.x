package com.ytjr.iservice.api;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;

public interface IUserService extends UserDetailsService {

    void deleteCache();

    Date nowTime();
}
