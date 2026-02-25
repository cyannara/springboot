package com.example.demo.hr.service;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("EmployeesDTO")
public class EmployeesDTO {
	private String employeeId;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private Date hireDate;
	private String jobId;
	private Long salary;
	private Double commissionPct;
	private String managerId;
	private String departmentId;
}
