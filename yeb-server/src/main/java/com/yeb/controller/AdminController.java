package com.yeb.controller;


import com.yeb.annotation.SystemLog;
import com.yeb.domain.RespBean;
import com.yeb.domain.pojo.Admin;
import com.yeb.domain.pojo.Role;
import com.yeb.service.IAdminService;
import com.yeb.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 操作员
 * </p>
 *
 * @author yuhui
 * @since 2023-04-02
 */
@RestController
@RequestMapping("/system/admin")
@Api(tags = "操作员 AdminController")
public class AdminController {

    @Autowired
    private IAdminService adminService;
    
    @Autowired
    private IRoleService roleService;
    
    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/")
    public List<Admin> getAllAdmin(String keywords){
        System.out.println("关键字为：" + keywords);
        return adminService.getAllAdmin(keywords);
    }
    
    @ApiOperation(value = "更新操作员")
    @SystemLog(businessName = "更新操作员")
    @PutMapping("/")
    public RespBean updateAdmin(@RequestBody Admin admin){
        if(adminService.updateById(admin)){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @ApiOperation(value = "删除操作员")
    @SystemLog(businessName = "删除操作员")
    @DeleteMapping("/{id}")
    public RespBean deleteAdmin(@PathVariable Integer id) {
        if (adminService.removeById(id)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    @ApiOperation(value = "更新操作员角色")
    @PutMapping("/role")
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        return adminService.addAdminRole(adminId, rids);
    }
}

