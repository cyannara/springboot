package com.exam;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
/*
 * 단위테스트 -> 통합테스트 -> 인수테스트
 * 클래스, front -> 메서드 컨트롤러 -> 서비스 -> 리포지토리
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext2.xml")
public class EmpServiceTest {
	
	@Autowired Restaurant restaurant;
	@Autowired EmpService empService;
	
	@Test
	public void test2() {
		restaurant.order();
	}
	
	@Test
	public void  test() {
		empService.register();
	}
}
