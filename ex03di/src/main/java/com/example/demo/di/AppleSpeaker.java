package com.example.demo.di;

import org.springframework.stereotype.Component;

//@Component
public class AppleSpeaker implements Speaker {
	
	public AppleSpeaker() {
		System.out.println("Apple Speaker 생성");
	}
	
	@Override
	public void volumeUp() {
		System.out.println("Apple Speaker 소리 올림");		
	}

	@Override
	public void volumeDown() {
		System.out.println("Apple Speaker 소리 내림");
	}
}
