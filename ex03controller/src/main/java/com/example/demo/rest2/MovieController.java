package com.example.demo.rest2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class MovieController {

	@GetMapping("/movie")
	public String movie(@RequestParam(defaultValue = "20250830", required = false) String date) {
		String uri = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=12664b24453335d2c3eca0fdc4b3b013&targetDt="
				+ date;
		RestTemplate restTemplate = new RestTemplate();
		JsonNode node = restTemplate.getForObject(uri, JsonNode.class);
		String name = node.get("boxOfficeResult").get("dailyBoxOfficeList").get(0).get("movieNm").asText();
		return name;
	}

	@GetMapping("/movie2")
	public String getPost() {
		WebClient webClient = WebClient.create();
		String uri = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=12664b24453335d2c3eca0fdc4b3b013&targetDt="
				+ "20250831";

		String response = webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(String.class)
				.block();
		return response;
	}

}
