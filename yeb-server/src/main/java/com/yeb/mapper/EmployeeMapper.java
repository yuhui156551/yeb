package com.yeb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeb.domain.RespBean;
import com.yeb.domain.pojo.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yuhui
 * @since 2023-04-02
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 获取所有员工
     * @param page
     * @param employee
     * @param beginDateScope
     * @return
     */
    Page<Employee> getEmployeeByPage(Page<Employee> page, Employee employee, LocalDate[] beginDateScope);

    /**
     * 查询员工
     * @param id
     * @return
     */
    List<Employee> getEmployee(Integer id);

    /**
     * 获取所有员工账套
     * @param page
     */
    Page<Employee> getEmployeeWithSalary(Page<Employee> page);
}
