package com.yeb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeb.domain.pojo.Admin;
import com.yeb.domain.pojo.Menu;
import com.yeb.mapper.MenuMapper;
import com.yeb.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuhui
 * @since 2023-04-02
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    
    /**
     * 通过用户id查询菜单列表
     *
     * @return
     */
    @Override
    public List<Menu> getMenuByAdminId() {
        // 从 SecurityContextHolder 获取用户信息
        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer adminId = admin.getId();
        // TODO 从 Redis 获取数据
        // 从数据库获取数据
        List<Menu> menus = baseMapper.getMenuByAdminId(adminId);
        return menus;
    }
}
