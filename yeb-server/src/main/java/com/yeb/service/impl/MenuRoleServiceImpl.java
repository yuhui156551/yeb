package com.yeb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeb.domain.RespBean;
import com.yeb.domain.pojo.MenuRole;
import com.yeb.mapper.MenuRoleMapper;
import com.yeb.service.IMenuRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuhui
 * @since 2023-04-02
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    @Override
    @Transactional
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        // 先删除角色的所有菜单
        baseMapper.delete(new QueryWrapper<MenuRole>().eq("rid", rid));
        if(null == mids || 0 == mids.length){
            return RespBean.success("更新成功！");
        }
        // 再给角色添加对应菜单
        Integer result = baseMapper.insertRecord(rid, mids);
        if(result == mids.length){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
