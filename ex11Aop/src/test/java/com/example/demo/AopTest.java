package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AopTest {

	@Autowired BoardService boardService;
	
	@Test
	public void test() {
		boardService.regiser();
	}
}