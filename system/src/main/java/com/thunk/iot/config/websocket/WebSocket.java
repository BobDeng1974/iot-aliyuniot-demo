package com.thunk.iot.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description:
 * @Date:     2019/5/10 10:38
 * Copyright (C), 2016-2019, EasyLinker V3
 */


@Configuration
/**
 * 注解开启使用STOMP协议来传输基于代理(message broker)的消息,
 * 这时控制器支持使用@MessageMapping,就像使用@RequestMapping一样
 */
@EnableWebSocketMessageBroker
public class WebSocket implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册STOMP协议的节点(endpoint),并映射指定的url
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * 注册一个STOMP的endpoint,并指定使用SockJS协议
         */
        registry.addEndpoint("/socket").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 配置消息代理(Message Broker)
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * 点对点应配置一个/user消息代理，广播式应配置一个/topic消息代理
         */
        registry.enableSimpleBroker("/topic", "/app");
        registry.setUserDestinationPrefix("/app");
    }
}
