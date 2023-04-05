package com.yeb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 * 
 * @author yuhui
 * @date 2023/4/4 14:52
 */
@RestController
public class HelloController {
    
    @GetMapping("hello")
    public String hello(){
        return "hello";
    }
}
