package com.yeb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeb.domain.RespBean;
import com.yeb.domain.RespPage;
import com.yeb.domain.pojo.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuhui
 * @since 2023-04-02
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 获取所有员工（分页）
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    RespPage getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    /**
     * 获取最大工号
     * @return
     */
    RespBean maxWorkID();

    /**
     * 添加员工
     * @return
     */
    RespBean addEmployee(Employee employee);

    /**
     * 查询员工
     * @param id
     */
    List<Employee> getEmployee(Integer id);

    /**
     * 获取员工所有账套
     * @param currentPage
     * @param size
     * @return
     */
    RespPage getEmployeeWithSalary(Integer currentPage, Integer size);
}
