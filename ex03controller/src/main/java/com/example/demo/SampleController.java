package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample/*")
//@Log4j2
public class SampleController {
	
	Logger log = LoggerFactory.getLogger(SampleController.class);
	
	@RequestMapping("")
	public String basic() {
		return "sample";
	}
	
	//@RequestMapping( value="basic", method = { RequestMethod.GET, RequestMethod.POST } )
	@GetMapping("basic")
	public String basicGet() {
		return "sample";
	}
	
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		log.info("dto" + dto);
		return "sample";
	}
	
	
}
