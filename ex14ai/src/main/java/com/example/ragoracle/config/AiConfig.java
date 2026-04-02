package com.example.ragoracle.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    /**
     * ChatClient 빈 등록
     * Spring AI 1.1.x에서는 ChatClient.Builder를 주입받아 빌드합니다.
     */
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                        당신은 업로드된 문서를 기반으로 질문에 답하는 AI 어시스턴트입니다.
                        제공된 컨텍스트(문서 내용)를 바탕으로 정확하고 친절하게 답변하세요.
                        컨텍스트에 없는 내용은 모른다고 명확히 말씀해 주세요.
                        답변은 한국어로 작성하세요.
                        """)
                .build();
    }
}
