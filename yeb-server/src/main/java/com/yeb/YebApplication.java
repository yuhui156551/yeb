package com.yeb;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author yuhui
 * @date 2023/4/2 20:09
 */
@SpringBootApplication
@MapperScan("com.yeb.mapper")// 注意别写成ComponentScan，找bug找了很久，泪目
public class YebApplication {
    public static void main(String[] args) {
        SpringApplication.run(YebApplication.class, args);
    }
}
