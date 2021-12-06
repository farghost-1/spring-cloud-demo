## 添加依赖

```XML

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>${spring.boot.version}</version>
</dependency>
```

`pom.xml`

## 添加注解

启动类添加@EnableWebSecurity注解

```Java

@SpringBootApplication
@EnableWebSecurity
public class SecurityStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityStartApplication.class, args);
    }
}
```

## @EnableWebSecurity注解分析

EnableWebSecurity导入了WebSecurityConfiguration

```Java

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({WebSecurityConfiguration.class, SpringWebMvcImportSelector.class, OAuth2ImportSelector.class,
        HttpSecurityConfiguration.class})
@EnableGlobalAuthentication
@Configuration
public @interface EnableWebSecurity {

    /**
     * Controls debugging support for Spring Security. Default is false.
     * @return if true, enables debug support with Spring Security
     */
    boolean debug() default false;

}
```

`org.springframework.security.config.annotation.web.configuration.EnableWebSecurity`

在用户没有自己实现WebSecurityConfigurerAdapter的情况下，它会缺省创建一个WebSecurityConfigurerAdapter加入到WebSecurity

```Java

@Configuration(proxyBeanMethods = false)
public class WebSecurityConfiguration implements ImportAware, BeanClassLoaderAware {
    @Bean(name = AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME)
    public Filter springSecurityFilterChain() throws Exception {
        boolean hasConfigurers = this.webSecurityConfigurers != null && !this.webSecurityConfigurers.isEmpty();
        boolean hasFilterChain = !this.securityFilterChains.isEmpty();
        Assert.state(!(hasConfigurers && hasFilterChain),
                "Found WebSecurityConfigurerAdapter as well as SecurityFilterChain. Please select just one.");
        if (!hasConfigurers && !hasFilterChain) {
            WebSecurityConfigurerAdapter adapter = this.objectObjectPostProcessor
                    .postProcess(new WebSecurityConfigurerAdapter() {
                    });
            this.webSecurity.apply(adapter);
        }
        for (SecurityFilterChain securityFilterChain : this.securityFilterChains) {
            this.webSecurity.addSecurityFilterChainBuilder(() -> securityFilterChain);
            for (Filter filter : securityFilterChain.getFilters()) {
                if (filter instanceof FilterSecurityInterceptor) {
                    this.webSecurity.securityInterceptor((FilterSecurityInterceptor) filter);
                    break;
                }
            }
        }
        for (WebSecurityCustomizer customizer : this.webSecurityCustomizers) {
            customizer.customize(this.webSecurity);
        }
        return this.webSecurity.build();
    }
}
```

`org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration`

再看下WebSecurityConfigurerAdapter的configure方法，可以看到它缺省配置了所有请求都需要认证

```Java
protected void configure(HttpSecurity http)throws Exception{
        this.logger.debug("Using default configure(HttpSecurity). "
        +"If subclassed this will potentially override subclass configure(HttpSecurity).");
        http.authorizeRequests((requests)->requests.anyRequest().authenticated());
        http.formLogin();
        http.httpBasic();
        }
```

