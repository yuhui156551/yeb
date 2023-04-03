package com.yeb.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户登录实体类
 * 
 * @author yuhui
 * @date 2023/4/3 10:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AdminLogin对象", description = "")
public class AdminLogin {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
