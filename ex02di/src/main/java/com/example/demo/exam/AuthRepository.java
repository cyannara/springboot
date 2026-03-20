package com.exam;

import org.springframework.stereotype.Component;

@Component
public class AuthRepository {
	public void insert() {
		System.out.println("권한등록");
	}
}
