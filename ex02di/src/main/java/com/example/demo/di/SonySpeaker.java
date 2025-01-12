package com.example.demo.di;
import org.springframework.stereotype.Component;

@Component
public class SonySpeaker implements Speaker {
	
	public SonySpeaker(){
		System.out.println("Sony Speaker 생성");
	}

	@Override
	public void volumeUp(){
		System.out.println("Sony Speaker 소리 올림");
	}

	@Override
	public void volumeDown(){
		System.out.println("Sony Speaker 소리 내림");
	}	
}
