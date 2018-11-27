package com.ytjr.utils;

import java.time.LocalDateTime;

public class TimeUtils {

    public static boolean isAfterNow(LocalDateTime expireTime) {
        return expireTime.isAfter(LocalDateTime.now());
    }

    public static void main(String[] args) {
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(60);
        System.out.println(isAfterNow(expireTime));
    }
}
