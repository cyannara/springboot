package com.example.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiApplication.class, args);
	}
	
	
//	@Bean
//	public CommandLineRunner runner(ChatClient.Builder builder) {
//		System.out.println(builder);
//		return args -> {
//			ChatClient chatClient = builder.build();
//			String response = chatClient.prompt("스티브잡스의 명언을 한 개 알려줘")
//					.call()
//					.content();
//			System.out.println("[결과] " + response);
//		};
//	}

}
