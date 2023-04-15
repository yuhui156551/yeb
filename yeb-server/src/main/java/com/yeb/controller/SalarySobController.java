package com.yeb.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yeb.domain.RespBean;
import com.yeb.domain.RespPage;
import com.yeb.domain.pojo.Employee;
import com.yeb.domain.pojo.Salary;
import com.yeb.service.IEmployeeService;
import com.yeb.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工账套
 *
 * @author yuhui
 * @date 2023/4/15 15:41
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobController {

    @Autowired
    private ISalaryService salaryService;

    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "获取所有工资账套（更新时需要显示选项）")
    @GetMapping("/salaries")
    public List<Salary> getAllSalaries() {
        return salaryService.list();
    }

    @ApiOperation(value = "获取所有员工账套(分页）")
    @GetMapping("/")
    public RespPage getEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer currentPage,
                                          @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getEmployeeWithSalary(currentPage, size);
    }

    @ApiOperation(value = "更新员工账套")
    @PutMapping("/")
    public RespBean updateEmployeeSalary(Integer eid, Integer sid) {
        boolean update = employeeService.update(new UpdateWrapper<Employee>().set("salary_id", sid).eq("id", eid));
        if (update) {
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
