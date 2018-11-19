package com.ytjr.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableCaching
@EnableRedisHttpSession
@SpringBootApplication
public class YtjrApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YtjrApiApplication.class, args);
    }
}
