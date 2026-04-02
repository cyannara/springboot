package com.example.ragoracle;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringResponse {

    @Autowired
    private ChatClient chatClient;

    @Test
    public void test() {

        String response = chatClient.prompt()
                .user("스티브 잡스의 명언을 한 개 알려줘")
                .call()
                .content();

        System.out.println(response);
        assertNotNull(response);
    }
}
