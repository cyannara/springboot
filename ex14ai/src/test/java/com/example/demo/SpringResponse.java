package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class SpringResponse {

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    private ChatClient chatClient;

    @BeforeEach
    void setUp() {
        this.chatClient = chatClientBuilder.build();
    }
	
	@Test
	public void test() {
	
        String response = chatClient.prompt("스티브 잡스의 명언을 한 개 알려줘")
                .call()
                .content();
        
        System.out.println(response);
	}
}
