package com.yeb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author yuhui
 * @date 2023/4/15 16:25
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * 添加这个 EndPoint，这样在网页可以通过 WebSocket 连接上服务
     * 也就是我们可以配置 WebSocket 的服务地址，并且可以指定是否使用 socketJS
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("ws/ep")// 将ws/ep注册为stomp的端点，用户连接了这个端点就可以进行WebSocket通讯
                .setAllowedOrigins("*")// 允许跨域
                .withSockJS();// 使用socketJS访问
    }

    /**
     * 配置消息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 配置代理域，配置代理目的地为/queue，可以在配置域上给客户端推送消息
        registry.enableSimpleBroker("/queue");
    }
}
