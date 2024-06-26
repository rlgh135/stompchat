package com.t1.tripfysub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

	/*
	 * Spring framework 및 Spring Security는 STOMP 를 사용하여 WebSocket만 사용할 때보다 더 다채로운
	 * 모델링을 할 수 있다.
	 * 
	 * Messaging Protocol을 만들고 메세지 형식을 커스터마이징 할 필요가 없다. RabbitMQ, ActiveMQ 같은
	 * Message Broker를 이용해, Subscription(구독)을 관리하고 메세지를 브로드캐스팅할 수 있다. WebSocket 기반으로
	 * 각 Connection(연결)마다 WebSocketHandler를 구현하는 것 보다 @Controller 된 객체를 이용해 조직적으로
	 * 관리할 수 있다. 즉, 메세지는 STOMP의 "destination" 헤더를 기반으로 @Controller
	 * 객체의 @MethodMapping 메서드로 라우팅 된다. STOMP의 "destination" 및 Message Type을 기반으로
	 * 메세지를 보호하기 위해 Spring Security를 사용할 수 있다.
	 */
	
	// endpoint 설정
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stomp/chat").setAllowedOrigins("http://localhost:8080").withSockJS();
	}

	/*
	 * /example는 WebSocket 또는 SockJS Client가 웹소켓 핸드셰이크 커넥션을 생성할 경로이다. /test 경로로 시작하는
	 * STOMP 메세지의 "destination" 헤더는 @Controller 객체의 @MessageMapping 메서드로 라우팅된다. 내장된
	 * 메세지 브로커를 사용해 Client에게 Subscriptions, Broadcasting 기능을 제공한다. 또한 /topic,
	 * /queue로 시작하는 "destination" 헤더를 가진 메세지를 브로커로 라우팅한다.
	 * 내장된 Simple Message Broker는 /topic, /queue prefix에 대해 특별한 의미를 부여하지 않는다.
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.setApplicationDestinationPrefixes("/pub");
		config.enableSimpleBroker("/sub");
	}
}
