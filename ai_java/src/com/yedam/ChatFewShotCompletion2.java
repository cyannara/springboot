package com.yedam;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

public class ChatFewShotCompletion2 {
    public static void main(String[] args) {
        // Configures using one of:
        // - The `OPENAI_API_KEY` environment variable
        // - The `OPENAI_BASE_URL` and `AZURE_OPENAI_KEY` environment variables
    	// fromEnv()가 OPENAI_API_KEY 값을 자동으로 읽어서 OpenAIClient 객체를 만들어 줍니다.
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        ChatCompletionCreateParams createParams = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4_1_NANO)           
                .maxCompletionTokens(10)
           //     .addUserMessage("병아리")
            //    .addAssistantMessage("삐약")
                .addUserMessage("호랑이")
          //      .reasoningEffort(ReasoningEffort.LOW)
                .build();

        
        client.chat()      //OpenAI Chat API를 사용하겠다고 선언
                .completions()  //모델에게 메시지 보내고 답변 받기
                .create(createParams)  //실제 요청 생성
                .choices()   //여러 개의 선택지(choices)가 있을 수 있음
                .stream()     //List를 Stream으로 변환
                .flatMap(choice -> choice.message().content().stream())  //content()는 리스트로 여러 텍스트 조각을 가질 수 있음. 
                                                                         //모든 choice 안의 content를 한 줄로 펼쳐서 스트림으로 처리
                                                                         //여러 choice → 여러 message → 여러 content → 모두 한 줄로 모음
                .forEach(System.out::println);   //최종 스트림의 모든 텍스트를 출력.
    }
}
