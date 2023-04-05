package com.yeb.controller;

import com.yeb.common.RespBean;
import com.yeb.domain.dto.AdminLogin;
import com.yeb.domain.pojo.Admin;
import com.yeb.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 用户登录
 * 
 * @author yuhui
 * @date 2023/4/3 10:46
 */
@RestController
@Api(tags = "LoginController")
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLogin adminLogin, HttpServletRequest request) {
        return adminService.login(adminLogin.getUsername(), adminLogin.getPassword(), request);
    }
    
    @ApiOperation(value = "获取当前登录用户的信息")
    @PostMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if(null == principal){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUsername(username);
        admin.setPassword(null);// 安全起见，不给前端返回密码
        return admin;
    } 

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout() {
        return RespBean.success("退出成功！");
    }
}
