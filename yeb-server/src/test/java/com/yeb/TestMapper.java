package com.yeb;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeb.domain.pojo.Admin;
import com.yeb.mapper.AdminMapper;
import com.yeb.service.IAdminService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;

/**
 * @author yuhui
 * @date 2023/4/4 22:51
 */
@SpringBootTest
public class TestMapper {
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private IAdminService adminService;
    @Resource
    private AdminMapper adminMapper;
    
    @Test
    void testUserDetail(){
        userDetailsService.loadUserByUsername("admin");
    }
    
    @Test
    void testMapper(){
//        adminService.getAdminByUsername("admin");
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", "admin"));
        System.out.println(admin);
    }

    @Test
    void testService(){
        Admin admin = adminService.getAdminByUsername("admin");
        System.out.println(admin);
    }
}
