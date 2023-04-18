package com.yeb.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 * 
 * @author yuhui
 * @date 2023/4/4 14:52
 */
@RestController
@Api(tags = "测试 HelloController")
public class HelloController {
    
    @GetMapping("hello")
    public String hello(){
        return "hello";
    }
}
