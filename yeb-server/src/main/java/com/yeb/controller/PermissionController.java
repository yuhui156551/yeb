package com.yeb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeb.annotation.SystemLog;
import com.yeb.domain.RespBean;
import com.yeb.domain.pojo.Menu;
import com.yeb.domain.pojo.MenuRole;
import com.yeb.domain.pojo.Role;
import com.yeb.service.IMenuRoleService;
import com.yeb.service.IMenuService;
import com.yeb.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限组
 *
 * @author yuhui
 * @date 2023/4/7 20:12
 */
@Api(tags = "权限组 PermissionController")
@RestController
@RequestMapping("/system/basic/permiss")
public class PermissionController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IMenuRoleService menuRoleService;

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/")
    public List<Role> getAllRole() {
        return roleService.list();
    }

    @ApiOperation(value = "添加角色")
    @SystemLog(businessName = "添加角色")
    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        if (roleService.save(role)) {
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @ApiOperation(value = "删除角色")
    @SystemLog(businessName = "删除角色")
    @DeleteMapping("/role/{rid}")
    public RespBean deletePosition(@PathVariable Integer rid) {
        if (roleService.removeById(rid)) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
    
    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenu(){
        // 菜单共有三级
        return menuService.getAllMenu();
    }

    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping("/mid/{rid}")
    public List<Integer> getAllMenu(@PathVariable Integer rid){
        List<MenuRole> menuRoles = menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid", rid));
        return menuRoles.stream().map(MenuRole::getMid).collect(Collectors.toList());
    }
    
    @ApiOperation(value = "更新角色菜单")
    @SystemLog(businessName = "更新角色菜单")
    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid, Integer[] mids){
        return menuRoleService.updateMenuRole(rid, mids);
    }
}
