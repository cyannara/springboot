package com.example.demo;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "예제 API", description = "Swagger 테스트용 API")
@RestController
@RequestMapping("api/")
public class MainRestController {

	@Operation(summary = "목록", description = "목록조회")
   // @Parameter(name = "str", description = "2번 반복할 문자열")
	@GetMapping("list")
	public Map<String, Object> list(){
		return Collections.singletonMap("success", true);
	}
	
	@Operation(summary = "단건", description = "단건조회")
	@GetMapping("one")
	public Map<String, Object> one(){
		return Collections.singletonMap("one", true);
	}
}
