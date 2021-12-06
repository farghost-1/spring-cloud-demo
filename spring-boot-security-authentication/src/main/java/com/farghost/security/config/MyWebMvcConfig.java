package com.farghost.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liucheng
 * @date 2021/12/6 19:37
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    /**
     * 未认证通过时默认url跳转设置.
     * /login为spring-cloud-security提供
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/login");
    }
}
