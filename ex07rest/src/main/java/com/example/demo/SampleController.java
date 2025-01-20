package com.example.demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SampleController {

	@GetMapping(value="/getText", produces = "text/plain;charset:UTF-8")
	public String getText() {
		return "안녕하세요";
	}
	
	@GetMapping(value="/getTextEntity")
	public ResponseEntity<String> getTextEntity() {
		return new ResponseEntity<>("안녕하세요", HttpStatus.BAD_GATEWAY);
	}
	
	@GetMapping("/getSample")
	public SampleVO getSample() {
		return new SampleVO(100,"길동","김", new Date());
	}
	
	@GetMapping("/getSampleEntity")
	public ResponseEntity<SampleVO> getSampleEntity() {
		SampleVO sample = new SampleVO(100,"길동","김", new Date());
		return new ResponseEntity<>(sample, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getMap")
	public Map<String,Object> getMap(){
		Map<String,Object> map = new HashMap<>();
		map.put("sample", new SampleVO(100,"길동","김", new Date()));
		map.put("total", 20);
		map.put("success", true);
		return map;		
	}
	
	@GetMapping("/product/{cat}/{pid}")
	public String[] getPath(@PathVariable String cat,
			                @PathVariable(name="pid") Integer prdid) {
		return new String[] {cat, ""+prdid};
	}
	
	@PostMapping("/ticket")
	public Ticket convert(
			 @RequestBody Ticket ticket) {
		log.info("ticket: "+ticket);
		return ticket;
	}
	
	@PostMapping("/comp")
	public CompVO comp(@RequestBody CompVO comp) {
		log.info("owner: " + comp.getList().get(0).getOwner());
		return comp;
	}
	
	
	@PostMapping("/compMap")
	public JsonNode compMap(
			@RequestBody JsonNode node) {
		//첫번째 티켓의 owner 출력
		log.info("owner:" + node.get("list").get(0).get("owner").asText() );
		return node;
	}
	

	
	@GetMapping("/movie")
	public String movie( @RequestParam(defaultValue = "20250115", required = false) String date) {
		RestTemplate restTemplate = new RestTemplate();
		String uri = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=12664b24453335d2c3eca0fdc4b3b013&targetDt="+ date;
		JsonNode node = restTemplate.getForObject(uri, JsonNode.class);
		String name = node.get("boxOfficeResult").get("dailyBoxOfficeList").get(0).get("movieNm").asText();
		return name;
	}
}





