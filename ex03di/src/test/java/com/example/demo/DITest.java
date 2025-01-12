package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.demo.di.Restaurant;
import com.example.demo.di.TV;

@SpringBootTest
public class DITest {
	
	@Autowired 
	ApplicationContext context;
	
	//@Autowired TV tv;
	

	
	@Test
	@DisplayName("스프링 컨테이너")
	public void test() {
		TV tv = context.getBean(TV.class);
		tv.powerOn();
		tv.volumeUp();
		tv.volumeDown();
		tv.powerOff();	
	}
	
	@Autowired Restaurant restaurant;
	
	//@Test
	public void Restauranttest() {
		assertNotNull(restaurant);
	}
	
	@Test
	public void springContainer() {
	    Restaurant rest1 = context.getBean(Restaurant.class);
	    Restaurant rest2 = context.getBean(Restaurant.class);
	    assertThat(rest1).isSameAs(rest2);
	}
}
