package com.exam;

import org.springframework.stereotype.Component;

@Component
public class EmpRepository {
	public void insert() {
		System.out.println("사원등록");
	}
}
