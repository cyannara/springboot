package com.container;
//Employee 테이블 구조

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Employee {

	private int employeeId;
	private String firstName;
	private String email;
	private long salary;
	private Date hireDate;

}
