package com.yeb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeb.common.RespBean;
import com.yeb.domain.pojo.Admin;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuhui
 * @since 2023-04-02
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 登录之后返回 token
     * @param username
     * @param password
     * @param request
     * @return
     */
    RespBean login(String username, String password, HttpServletRequest request);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    Admin getAdminByUsername(String username);
}
