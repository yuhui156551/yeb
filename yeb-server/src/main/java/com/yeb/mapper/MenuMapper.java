package com.yeb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeb.domain.pojo.Menu;
import com.yeb.domain.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yuhui
 * @since 2023-04-02
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过用户id查询菜单列表
     * @param adminId
     * @return
     */
    List<Menu> getMenuByAdminId(Integer adminId);

    /**
     * 根据角色查询菜单列表
     * @return
     */
    List<Menu> getMenuWithRole();

    /**
     * 查询所有菜单
     * @return
     */
    List<Menu> getAllMenu();
}
