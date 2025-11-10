package com.example.demo.web;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;


@RestController
public class BoardRestController {

	@GetMapping("/api")
	public Map test(HttpServletRequest request) {
		String ajax = request.getHeader("x-requested-with");
		System.out.println("ajax>> " + ajax);
		return Collections.singletonMap("result", true);	
	}
}
