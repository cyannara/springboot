package com.yedam;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

public class ResponseExampleTempature {
	public static void main(String[] args) {

			
		String rolePrompt = """
				너는 초등학생에게 설명하는 친절한 선생님입니다.
				- 쉬운 단어만 사용
				- 예시를 들어서 설명
				- 문장은 2~3문장으로 제한
				""";
				
		OpenAIClient client = OpenAIOkHttpClient.fromEnv();
		ResponseCreateParams params = ResponseCreateParams.builder()
				.model(ChatModel.GPT_4_1_NANO) 
		        .maxOutputTokens(500)
		        .temperature(0)
		        .instructions(rolePrompt)         
		        .input("인터넷이 어떻게 작동하는지 알려줘")  
		        .build();

		Response response = client.responses().create(params);

		String result = response.output().get(0)
		        .message().get()
		        .content().get(0)
		        .outputText().get()
		        .text();
		
		System.out.println(result);
		
		
		// 결과 읽기
//        response.output().stream()
//        .flatMap(item -> item.message().stream())
//        .flatMap(message -> message.content().stream())
//        .flatMap(content -> content.outputText().stream())
//        .forEach(outputText -> System.out.println(outputText.text()));
//		
	}
}
