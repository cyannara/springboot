package com.example.demo.rest1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.JsonNode;

//@Log4j2
@Controller
public class Rest1Controller {
	
	Logger log = LoggerFactory.getLogger(Rest1Controller.class);
	
	@PostMapping("/ticket")
	public Ticket convert( @RequestBody Ticket ticket) {
		
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
