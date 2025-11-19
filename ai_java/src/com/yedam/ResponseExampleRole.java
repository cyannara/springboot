package com.yedam;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

public class ResponseExampleRole {
	public static void main(String[] args) {

		String rolePrompt = """
				너는 백설공주 이야기 속 마법거울이야
				""";
		
		//String rolePrompt = "너는 영화 배트맨 속의 조커야."; 
		OpenAIClient client = OpenAIOkHttpClient.fromEnv();
		ResponseCreateParams params = ResponseCreateParams.builder()
				.model(ChatModel.GPT_4_1_NANO) 
		        .maxOutputTokens(500)
		        .instructions(rolePrompt)          // role_prompt 에 해당
		        .input("블세상에서 누가 제일 아름답니?")  
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
