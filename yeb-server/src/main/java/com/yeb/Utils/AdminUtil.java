package com.yeb.Utils;

import com.yeb.domain.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 操作员工具类
 * 
 * @author yuhui
 * @date 2023/4/13 19:03
 */
public class AdminUtil {
    
    public static Admin getCurrentAdmin(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
