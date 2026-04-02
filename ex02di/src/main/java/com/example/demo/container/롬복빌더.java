package com.container;

public class 롬복빌더 {

	public static void main(String[] args) {
		
		Employee emp = Employee.builder()
				.employeeId(100)
				.email("")
				.build();
	}
}
