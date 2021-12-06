package com.farghost.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author liucheng
 * @date 2021/12/6 19:49
 */
@Configuration
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrg跨域检查
        http.csrf().disable()
                // 配置授权
                .authorizeRequests()
                .antMatchers("/pm/**").hasRole("manager")
                .antMatchers("/traffic/**").hasRole("operator")
                //login下的请求直接通过
                .antMatchers("/login").permitAll()
                //其他请求需要登录
                .anyRequest().authenticated()
                //并行条件
                .and()
                //登陆成功跳转到/success, 失败跳转到/failure
//                .formLogin().defaultSuccessUrl("/success").failureUrl("/failure");
                .formLogin().defaultSuccessUrl("/user/getLoginUserByPrincipal").failureUrl("/failure");
    }
}
