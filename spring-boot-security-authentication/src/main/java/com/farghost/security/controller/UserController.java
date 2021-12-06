package com.farghost.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author liucheng
 * @date 2021/12/6 18:38
 */
@RestController
@RequestMapping("user")
public class UserController {
    @GetMapping("/getLoginUserByPrincipal")
    public String getLoginUserByPrincipal(Principal principal) {
        return principal.getName();
    }

    @GetMapping(value = "/getLoginUserByAuthentication")
    public String getLoginUserByAuthentication(Authentication authentication) {
        return authentication.getName();
    }

    @GetMapping(value = "/getLoginUserByHttpServletRequest")
    public String getLoginUserByHttpServletRequest(HttpServletRequest request) {
        return request.getUserPrincipal().getName();
    }

    @GetMapping("/getLoginUserBySecurityContextHolder")
    public String getLoginUserBySecurityContextHolder() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }
}
