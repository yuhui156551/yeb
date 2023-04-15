package com.yeb.controller;

import com.yeb.annotation.SystemLog;
import com.yeb.domain.RespBean;
import com.yeb.domain.pojo.Admin;
import com.yeb.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 个人中心
 * 
 * @author yuhui
 * @date 2023/4/15 17:12
 */
@RestController
@Api(tags = "个人中心 AdminInfoController")
@RequestMapping("/admin")
public class AdminInfoController {
    
    @Autowired
    private IAdminService adminService;
    
    @ApiOperation(value = "更新用户信息")
    @SystemLog(businessName = "更新用户信息")
    @PutMapping("/info")
    public RespBean updateAdmin(@RequestBody Admin admin, Authentication authentication){
        if(adminService.updateById(admin)){
            // 更新成功重新设置 Authentication
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(admin, null, authentication.getAuthorities()));
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
    
    @ApiOperation(value = "更新用户密码")
    @SystemLog(businessName = "更新用户密码")
    @PutMapping("/pass")
    public RespBean updatePassword(@RequestBody Map<String, Object> info){
        String oldPass = (String) info.get("oldPass");
        String pass = (String) info.get("pass");
        Integer adminId = (Integer) info.get("adminId");
        return adminService.updateAdminPassword(oldPass,pass,adminId);
    }
}
