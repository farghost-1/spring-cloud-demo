SpringBoot集成SpringSecurity实现认证和授权

authentication：认证

authorization：授权

## 自定义PasswordEncoder

BCryptPasswordEncoder是SpringSecurity中最常用的密码解析器。它使用BCrypt算法。它的特点是加密可以加盐salt，但是解密不需要盐。因为盐就在密文当中。这样可以通过每次添加不同的盐，而给同样的字符串加密出不同的密文。
密文形如：$2a$10$vTUDYhjnVb52iM3qQgi2Du31sq6PRea6xZbIsKIsmOVDnEuGb/.7K
其中：$是分割符，无意义；2a是bcrypt加密版本号；10是cost的值；而后的前22位是salt值；再然后的字符串就是密码的密文了

```Java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

## 自定义UserDetailsService

这里测试使用InMemoryUserDetailsManager，存在内存中

有两个用户，分别是admin和test

admin的role为manager和operator

test的role为operator

```Java
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
```

## 自定义WebSecurityConfigurerAdapter

/pm开始的url需要manager的role才能访问

/traffic开始的url需要operator的role才能访问

这里因为直接使用SpringSecurity的/login来登陆，所以需要放开/login

成功跳转到/success

```Java
@Override
protected void configure(HttpSecurity http) throws Exception {
    //关闭csrg跨域检查
    http.csrf().disable()
            //配置资源权限
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
            .formLogin().defaultSuccessUrl("/success").failureUrl("/failure");
}
```



