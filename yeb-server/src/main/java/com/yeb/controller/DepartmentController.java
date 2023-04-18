package com.yeb.controller;


import com.yeb.annotation.SystemLog;
import com.yeb.domain.RespBean;
import com.yeb.domain.pojo.Department;
import com.yeb.service.IDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  部门管理
 * </p>
 *
 * @author yuhui
 * @since 2023-04-02
 */
@RestController
@RequestMapping("/system/basic/department")
@Api(tags = "部门管理 DepartmentController")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;
    
    @ApiOperation(value = "获取所有部门")
    @GetMapping("/")
    public List<Department> getAllDepartment(){
        // 后续会传递 -1 作为 parentId，随后进行递归查询，获取树结构
        return departmentService.getAllDepartment();
    }
    
    @ApiOperation(value = "添加部门")
    @SystemLog(businessName = "添加部门")
    @PostMapping("/")
    public RespBean addDep(@RequestBody Department dep){
        return departmentService.addDep(dep);
    }
    
    @ApiOperation(value = "删除部门")
    @SystemLog(businessName = "删除部门")
    @DeleteMapping("/{id}")
    public RespBean deleteDep(@PathVariable Integer id){
        return departmentService.deleteDep(id);
    }
}

