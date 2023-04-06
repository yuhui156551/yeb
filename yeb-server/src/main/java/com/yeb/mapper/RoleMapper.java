package com.yeb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id获取角色列表
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);
}
