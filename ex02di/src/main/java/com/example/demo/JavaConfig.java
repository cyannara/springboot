package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.di.AppleSpeaker;
import com.example.demo.di.Chef;
import com.example.demo.di.LgTV;
import com.example.demo.di.Restaurant;
import com.example.demo.di.Speaker;
import com.example.demo.di.TV;

@Configuration
public class JavaConfig {

	@Bean
	Chef chef() {
		return new Chef();
	}
	
	@Bean
	Restaurant restaurnt() {
		return new Restaurant(chef());
	}
	
	@Bean
	Speaker speaker() {
		return new AppleSpeaker();
	}

	@Bean
	TV tv() {
		return new LgTV(speaker());
	}
}
