package com.farghost.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author liucheng
 * @date 2021/12/6 11:31
 */
@SpringBootApplication
@EnableWebSecurity
public class SecurityStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityStartApplication.class, args);
    }
}
