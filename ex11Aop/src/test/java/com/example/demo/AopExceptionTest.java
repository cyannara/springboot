package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.board.BoardService;

@SpringBootTest
public class AopExceptionTest {

	@Autowired BoardService boardService;
	
	@Test
	public void test() {
		boardService.select(0);
	}
}