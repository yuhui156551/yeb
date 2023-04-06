package com.yeb.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeb.domain.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常
 * 
 * @author yuhui
 * @date 2023/4/3 15:17
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        RespBean respBean = RespBean.error("您尚未登录，请登录!");
        respBean.setCode(401);
        response.getWriter().write(new ObjectMapper().writeValueAsString(respBean));
    }
}
