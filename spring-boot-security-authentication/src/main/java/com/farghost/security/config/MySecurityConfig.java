package com.farghost.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author liucheng
 * @date 2021/12/6 19:55
 */
@Configuration
public class MySecurityConfig {
    /**
     * 自定义PasswordEncoder
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 自定义UserDetailsService
     *
     * @param passwordEncoder
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager memoryUserDetailsManager = new InMemoryUserDetailsManager();
        UserDetails adminUser = User.withUsername("admin").password(passwordEncoder.encode("admin")).roles(
                "manager", "operator").build();
        UserDetails operatorUser = User.withUsername("test").password(passwordEncoder.encode("test")).roles(
                "operator").build();
        memoryUserDetailsManager.createUser(adminUser);
        memoryUserDetailsManager.createUser(operatorUser);
        return memoryUserDetailsManager;
    }
}
