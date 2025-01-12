package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/sample/*")
@Log4j2
public class SampleController {
	
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
