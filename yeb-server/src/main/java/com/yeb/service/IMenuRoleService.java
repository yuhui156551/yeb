package com.yeb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeb.domain.RespBean;
import com.yeb.domain.pojo.MenuRole;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuhui
 * @since 2023-04-02
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
