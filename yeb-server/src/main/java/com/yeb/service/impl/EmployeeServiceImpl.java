package com.yeb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeb.domain.RespBean;
import com.yeb.domain.RespPage;
import com.yeb.domain.pojo.Employee;
import com.yeb.mapper.EmployeeMapper;
import com.yeb.service.IEmployeeService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuhui
 * @since 2023-04-02
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 获取所有员工（分页）
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    @Override
    public RespPage getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        Page<Employee> page = new Page<>(currentPage, size);
        // 在调用 getEmployeeByPage()方法时，它会返回一个经过分页处理的查询结果，将这个查询结果赋值给 employeePage
        // page 对象作为参数传入，表示查询结果需要进行分页处理
        Page<Employee> employeePage = baseMapper.getEmployeeByPage(page, employee, beginDateScope);
        return new RespPage(employeePage.getTotal(), employeePage.getRecords());
    }

    /**
     * 获取最大工号
     * @return
     */
    @Override
    public RespBean maxWorkID() {
        List<Map<String, Object>> maps = baseMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));
        int maxWorkID = Integer.parseInt(maps.get(0).get("max(workID)").toString());
        String format = String.format("%08d", maxWorkID + 1);// 00000001
        return RespBean.success(null, format);
    }

    /**
     * 添加员工
     * @return
     */
    @Override
    public RespBean addEmployee(Employee employee) {
        LocalDate beginDate = employee.getBeginDate();
        LocalDate endContract = employee.getEndContract();
        // 计算相差多少天
        long days = beginDate.until(endContract, ChronoUnit.DAYS);
        // 保留两位小数
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        // 转换为 年 为单位
        String format = decimalFormat.format(days / 365.00);
        // 设置在职年限
        employee.setContractTerm(Double.parseDouble(format));
        if(1 == baseMapper.insert(employee)){
            // 发送消息
            Employee emp = baseMapper.getEmployee(employee.getId()).get(0);
            rabbitTemplate.convertAndSend("mail.welcome", emp);
            
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 查询员工
     * @param id
     * @return
     */
    @Override
    public List<Employee> getEmployee(Integer id) {
        return baseMapper.getEmployee(id);
    }

    /**
     * 获取所有员工账套
     * @param currentPage
     * @param size
     * @return
     */
    @Override
    public RespPage getEmployeeWithSalary(Integer currentPage, Integer size) {
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeePage = baseMapper.getEmployeeWithSalary(page);
        return new RespPage(employeePage.getTotal(), employeePage.getRecords());
    }
}
