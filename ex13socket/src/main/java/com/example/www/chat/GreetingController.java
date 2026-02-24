package com.example.www.chat;

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

	@ResponseBody
	@GetMapping("/cust")
	public Map<String, String> cust() {
		String text = "[" + new Date() + "]:" + "cust select";
		AlamVO alamVO = new AlamVO("notice","admin", text);
		this.template.convertAndSend("/topic/notice", alamVO);
		return Collections.singletonMap("name", "test");
	}

	@GetMapping("insert")
	@ResponseBody
	public String insert(String greeting) {
		//서비스

		//메시지 전송 
		String text = "[" + new Date() + "]:" + "승인요청 ";
		this.template.convertAndSendToUser(text, "/topic/approve", text); 
		return "ok";
	}
}
