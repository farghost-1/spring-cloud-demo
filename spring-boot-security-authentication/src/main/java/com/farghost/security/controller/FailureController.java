package com.farghost.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liucheng
 * @date 2021/12/6 18:38
 */
@RestController
@RequestMapping("failure")
public class FailureController {
    @GetMapping
    public void failure() {
        System.out.println("Limit of authority.");
    }
}
