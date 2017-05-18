package com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
@ComponentScan(basePackages="com.*")
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer{
	public void configureClientInboundChannel(ChannelRegistration arg0) {
	}

	public void configureClientOutboundChannel(ChannelRegistration arg0) {
    }

	public void configureMessageBroker(MessageBrokerRegistry configurer) {
		configurer.enableSimpleBroker("/queue/","/topic/");
		configurer.setApplicationDestinationPrefixes("/app");
	}

	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/portfolio").withSockJS();
    }

}