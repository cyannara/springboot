package com.java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.factory.TV;

public class 자바기반설정 {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = 
	new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		TV tv = (TV)context.getBean("tv");
		tv.powerOn();
		tv.volumeUp();
	}
}
