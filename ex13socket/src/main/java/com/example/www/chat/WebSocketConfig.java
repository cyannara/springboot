package com.example.www.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	  @Override
	  public void configureMessageBroker(MessageBrokerRegistry config) {
	    config.enableSimpleBroker("/topic","/queue");   //구독신청(받기)
	    config.setApplicationDestinationPrefixes("/app");   //소켓메시지전송(보내기)
	    //config.setUserDestinationPrefix("/user")
	    ;
	  }

	  @Override
	  public void registerStompEndpoints(StompEndpointRegistry registry) {
	    registry.addEndpoint("/chatserver")   //소켓서버연결
	    //.addInterceptors(new HttpSessionHandshakeInterceptor())
	    ; 
	  }
}
