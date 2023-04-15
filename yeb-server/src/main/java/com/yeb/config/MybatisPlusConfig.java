package com.yeb.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分页插件
 * 分页查询是通过拦截器实现的。当我们执行分页查询时，MyBatis-Plus 会自动拦截 SQL 语句，并在其前后加入分页相关的 SQL 语句，
 * 以实现分页查询的功能。具体来说，MyBatis-Plus 会根据传入的 Page 对象计算出需要查询的记录范围，
 * 然后在 SQL 语句中加入 limit 和 offset 等关键字，以限制查询结果的数量和范围。
 * 
 * @author yuhui
 * @date 2023/4/14 17:27
 */
@Configuration
public class MybatisPlusConfig {
    
    @Bean
    public MybatisPlusInterceptor paginationInnerInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
