## web socket

- [spring framework storm](https://docs.spring.io/spring-framework/reference/6.2/web/websocket/stomp/overview.html)

## STORM
- Simple(or Streaming) Text Oriented Messaging Protocol
- 텍스트 기반 프로토콜
- STOMP 클라이언트가 프로토콜을 지원하는 모든 메시지 브로커와 통신할 수 있도록 상호 운용 가능한 와이어 형식을 제공합니다 
- STOMP는 하위 수준의 WebSocket 위에 작동하는 하위 프로토콜

### 메시지 브로커(메시지 지향 미들웨어 제품)
- Spring Framework
- `RabbitMQ`
- Apache ActiveMQ
- Fuse Message Broker
- HornetQ
- Open Message Queue (OpenMQ)
- syslog-ng

### [구독중인 사용자 모두에게 메시지 보내기](https://docs.spring.io/spring-framework/reference/6.2/web/websocket/stomp/handle-send.html)
```java
@Controller
public class GreetingController {

	private SimpMessagingTemplate template;

	@Autowired
	public GreetingController(SimpMessagingTemplate template) {
		this.template = template;
	}

	@RequestMapping(path="/greetings", method=POST)
	public void greet(String greeting) {
		String text = "[" + getTimestamp() + "]:" + greeting;
		this.template.convertAndSend("/topic/greetings", text);
	}

}
```

### [특정 사용자에게 메시지 보내기](https://docs.spring.io/spring-framework/reference/6.2/web/websocket/stomp/user-destination.html)
- [참고](https://asa9874.tistory.com/699)

WebSocketConfig 설정파일에 `config.setUserDestinationPrefix("/user");` 를 추가하여 특정 사용자에게 메시지를 보내는 목적지 접두사를 지정합니다.

```java
@Override
public void configureMessageBroker(@NonNull MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic","/queue");
    config.setApplicationDestinationPrefixes("/app");
    config.setUserDestinationPrefix("/user");
}
```

PrincipalHandshakeHandler 구현  
```java
public class PrincipalHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        String userId = attributes.get("userId").toString();
        return new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
    }
}
```
구독신청
```
/user/{username}/queue/position-updates
```

```java
@RequireArguemntConstructor
@Controller
public class PortfolioController {

  private final SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/trade")
	@SendToUser("/queue/position-updates")
	public TradeResult executeTrade(Trade trade, Principal principal) {
		// ...
		return tradeResult;
	}

  public TradeResult executeTrade(Trade trade) {
  		this.messagingTemplate.convertAndSendToUser(
				trade.getUserName(), "/queue/position-updates", trade.getResult());
  }
}
```
```java

```


