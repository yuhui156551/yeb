package com.yeb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2配置类
 * 
 * @author yuhui
 * @date 2023/4/4 14:40
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 指定哪个包下面生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.yeb.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes()); // 配置请求头信息
    }

    /**
     * 文档基本信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .version("1.0")
                .title("云E办接口文档")
                .description("云E办接口文档")
                .contact(new Contact("yuhui", "xxx", "536430462@qq.com"))
                .build();
    }

    /**
     * 解决访问接口登录问题
     * 
     * @return
     */
    private List<ApiKey> securitySchemes() {
        // 设置请求头信息
        List<ApiKey> result = new ArrayList<>();
        // 参数：api key 名字 { 准备的 key 名字，value 请求头 }
        result.add(new ApiKey("Authorization", "Authorization", "header"));
        return result;
    }

    // 2. 解决访问接口登录问题
    private List<SecurityContext> securityContexts() {
        // 设置需要登录认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath());
        return result;
    }

    /**
     * 获取需要登录认证的路径
     * 
     * @return
     */
    private SecurityContext getContextByPath() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/hello/.*"))
                .forPaths(PathSelectors.regex("/register/.*"))
                .build();
    }

    /**
     * 设置默认授权
     * 
     * @return
     */
    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization", scopes));
        return result;
    }
}
