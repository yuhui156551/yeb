package com.yeb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeb.domain.pojo.Admin;
import com.yeb.domain.pojo.Menu;
import com.yeb.domain.pojo.Role;
import com.yeb.mapper.MenuMapper;
import com.yeb.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuhui
 * @since 2023-04-02
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 通过用户id查询菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenuByAdminId() {
        // 从 SecurityContextHolder 获取用户信息
        Integer adminId = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        // 从 redis 获取数据
        List<Menu> menus = (List<Menu>) redisTemplate.opsForValue().get("menu_" + adminId);
        // 如果在 redis 中获取不到，就从数据库获取，并将数据存入 redis
        if (CollectionUtils.isEmpty(menus)) {
            menus = baseMapper.getMenuByAdminId(adminId);
            redisTemplate.opsForValue().set("menu_" + adminId, menus);
        }
        return menus;
    }

    /**
     * 根据角色查询菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenuWithRole() {
        return baseMapper.getMenuWithRole();
    }
}
