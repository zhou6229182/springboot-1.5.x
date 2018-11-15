package com.ytjr.api.utils;

import com.ytjr.entity.api.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static UserEntity getCurrentHr() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(null);
        return user;
    }
}
