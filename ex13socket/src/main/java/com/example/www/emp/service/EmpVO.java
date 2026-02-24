package com.example.www.emp.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpVO {
	String employeeId;
	String firstName;
	String lastName;
	String email;
	Timestamp   hireDate;
	String managerId;
	BigDecimal    salary;
	String jobId;
	String departmentId;
	String profile;
	//MultipartFile uploadFile;
}
