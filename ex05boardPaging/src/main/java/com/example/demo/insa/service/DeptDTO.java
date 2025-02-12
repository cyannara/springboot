package com.example.demo.insa.service;

import lombok.Data;

@Data
public class DeptDTO {
	long departmentId;
	String departmentName;
	long locationId;
	long managerId;
}
