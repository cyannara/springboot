package com.example.demo.rest2;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.rest1.Reserve;
import com.example.demo.rest1.Ticket;
import com.example.demo.rest1.User;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/*")
@Slf4j
@RestController
public class Rest2Controller {
	
	@GetMapping(value="/getText", produces = "text/plain;charset:UTF-8")
	public String getText() {
		return "안녕하세요";
	}
	
	@GetMapping(value = "/getText", produces = "application/json;charset=UTF-8" )
	public String getjson() {
		return  "[{\"greet\":\"안녕하세요\"}]"; 
	}
	
	@GetMapping("/check")
	public ResponseEntity<User> check(Double height, Double weight){
		User vo =new User(0,"","",null); //new UserVO(0, ""+height, ""+weight );
		ResponseEntity<User> result =  null;
		if(height > 150) {
			result =  ResponseEntity.status(HttpStatus.OK).body(vo);
		} else {
			result =  new ResponseEntity<>(vo, HttpStatus.BAD_GATEWAY);
		}
		return result;	
	}

	@GetMapping(value="/getTextEntity")
	public ResponseEntity<String> getTextEntity() {
		return new ResponseEntity<>("안녕하세요", HttpStatus.BAD_GATEWAY);
	}
	
	@GetMapping("/getSample")
	public User getSample() {
		return new User(100,"길동","김", new Date());
	}
	
	@GetMapping("/getSampleEntity")
	public ResponseEntity<User> getSampleEntity() {
		User sample = new User(100,"길동","김", new Date());
		return new ResponseEntity<>(sample, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getMap")
	public Map<String,Object> getMap(){
		Map<String,Object> map = new HashMap<>();
		map.put("sample", new User(100,"길동","김", new Date()));
		map.put("total", 20);
		map.put("success", true);
		return map;		
	}
	
	@GetMapping("/product/{cat}/{pid}")
	public String[] getPath(@PathVariable String cat,
			                @PathVariable(name="pid") Integer prdid) {
		return new String[] {cat, ""+prdid};
	}
	


}





