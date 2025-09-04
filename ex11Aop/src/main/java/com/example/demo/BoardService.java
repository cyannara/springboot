package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class BoardService {

	@PrintExecutionTime
	public void regiser() {
		for(int i=0; i<100000000; i++) {
			
		}
	}
	
}
