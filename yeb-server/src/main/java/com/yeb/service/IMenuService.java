package com.yeb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeb.domain.pojo.Menu;
import com.yeb.domain.pojo.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuhui
 * @since 2023-04-02
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 通过用户id查询菜单列表
     * @return
     */
    List<Menu> getMenuByAdminId();

    /**
     * 根据角色查询菜单列表
     * @return
     */
    List<Menu> getMenuWithRole();
}
