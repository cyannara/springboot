package com.example.demo.rest1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Tag(name = "Ticket API", description = "티켓 관리 기능 제공")
@RequestMapping("/api")
@Log4j2
@RestController
public class Rest1Controller {
		
	@Tag(name= "Ticket API")	
	@Operation(summary = "티켓조회", description = "티켓정보를 조회합니다.",         responses = {
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
        })
	@GetMapping("/ticket")
	public Ticket convert1(@RequestBody Ticket ticket) {
		
		log.info("ticket: "+ticket);
		return ticket;
		
	}
	
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		
		log.info("ticket: "+ticket);
		return ticket;
		
	}
	
	@PostMapping("/reserve")
	public Reserve comp(@RequestBody Reserve reserve) {
		
		log.info("owner: " + reserve.getList().get(0).getOwner());
		return reserve;
	}
	
	
	@PostMapping("/compMap")
	public JsonNode compMap(@RequestBody JsonNode node) {
		
		//첫번째 티켓의 owner 출력
		log.info("owner:" + node.get("list").get(0).get("owner").asText() );
		return node;
	}
	

}
