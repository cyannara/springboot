package com.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.container.Speaker;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@Component
public class SamsungTV implements TV {
	
	//1.생성자주입방법
	//final Speaker speaker;    //의존관계 객체
	
	//2.setter 주입
	@Setter(onMethod_ = {@Autowired} )
	
	//3필드주입
	@Autowired
	Speaker speaker;    //의존관계 객체
	int model;
	
	

	@Override
	public void powerOn() {
		System.out.println("SamsungTV powerOn");
	}

	@Override
	public void powerOff() {
		System.out.println("SamsungTV powerOff");
	}

	@Override
	public void volumeUp() {
		speaker.volumeUp();
	}

	@Override
	public void volumeDown() {
		speaker.volumeDown();
	}
}
