package com.example.demo.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardRestController {

	@Autowired
	BoardService boardService;
	
	@GetMapping("/api")
	public String select(@RequestParam Integer num) {
		boardService.select(num);
		return "board";
	}
}
