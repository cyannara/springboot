package com.example.demo.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@GetMapping("/board")
	public String select(@RequestParam(required = false, defaultValue = "1") Integer num) {
		boardService.select(num);
		return "board";
	}
	
	@GetMapping("/boarderror")
	public String select1() {
		int r = 5/0;
		return "board";
	}	
}
