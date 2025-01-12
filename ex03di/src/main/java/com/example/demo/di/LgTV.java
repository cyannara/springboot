package com.example.demo.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


//@Component
//@RequiredArgsConstructor
public class LgTV implements TV {

	//@Autowired
//	@Setter(onMethod_ = {@Autowired})
	private  Speaker speaker;
	
//	@Autowired
	public LgTV(Speaker speaker) {
		this.speaker = speaker;
	}
	
//	@Autowired
//	public void setSpeaker(Speaker speaker) {
//		this.speaker = speaker;
//	}
	
	public void powerOn() {
		System.out.println("LG TV--전원 on");
	}
	public void powerOff() {
		System.out.println("LG TV--전원 off");
	}
	public void volumeUp() {
		speaker.volumeUp();
		//System.out.println("LG TV--볼륨 up");
	}
	public void volumeDown() {
		speaker.volumeDown();
		//System.out.println("LG TV--볼륨 down");
	}
}
