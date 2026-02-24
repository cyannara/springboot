package com.example.demo.board;

import org.springframework.stereotype.Service;

import com.example.demo.annotation.PrintExecutionTime;

@Service
public class BoardService {

	public int select(int a) {
		return 5/a;
	}
	
	@PrintExecutionTime
	public void regiser() {
		for(int i=0; i<100000000; i++) {
			
		}
	}
	
}
