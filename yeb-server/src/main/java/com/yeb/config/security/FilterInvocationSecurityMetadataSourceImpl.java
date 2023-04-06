package com.yeb.config.security;

import com.yeb.domain.pojo.Menu;
import com.yeb.domain.pojo.Role;
import com.yeb.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 根据请求url分析请求所需角色
 *
 * @author yuhui
 * @date 2023/4/6 18:20
 */
@Component
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IMenuService menuService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取请求url
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        // 获取菜单（包含对应角色）
        List<Menu> menuWithRole = menuService.getMenuWithRole();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (Menu menu : menuWithRole) {
            // 判断请求url与菜单url是否匹配
            if(antPathMatcher.match(menu.getUrl(), requestUrl)){
                // 如果匹配，将成功匹配的菜单对应的角色保存到 ConfigAttribute
                String[] roles = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                SecurityConfig.createList(roles);
            }
        }
        // 如果匹配不上，默认给一个角色：登录之后就可以访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
