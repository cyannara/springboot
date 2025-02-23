package com.example.demo.chat;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

	private SimpMessagingTemplate template;

	@Autowired
	public GreetingController(SimpMessagingTemplate template) {
		this.template = template;
	}

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}

	@GetMapping("/cust")
	public Map<String, String> cust() {
		String text = "[" + new Date() + "]:" + "cust select";
		this.template.convertAndSend("/topic/cust", text);
		return Collections.singletonMap("name", "test");
	}

	@GetMapping("/insertTest")
	@ResponseBody
	public String insert(String greeting) {
		//서비스
		System.out.println(">>>>>>socket test");
		//메시지 전송 
		this.template.convertAndSendToUser("user3", "/topic/approve", "개인메시지"); 
		this.template.convertAndSend("/topic/approve", "전체메시지"); 
		return "ok";
	}
}
