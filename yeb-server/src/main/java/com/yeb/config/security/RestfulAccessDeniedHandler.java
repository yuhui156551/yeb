package com.yeb.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeb.common.RespBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权异常
 * 
 * @author yuhui
 * @date 2023/4/3 15:23
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        RespBean respBean = RespBean.error("权限不足，请联系管理员！");
        response.getWriter().write(new ObjectMapper().writeValueAsString(respBean));
    }
}
