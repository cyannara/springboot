package com.example.demo;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dao.EmployeeDAO;
import com.example.demo.service.EmployeesDto;


@SpringBootTest
public class EmplpyeeDaoTest {

	@Autowired
	EmployeeDAO dao;
	
	@Test
	public void test() {
		//given
		Long employeeId = 100l;
		
		//when
		EmployeesDto dto =  dao.findById(employeeId);
		System.out.println(dto);
		
		//then
		assertEquals(dto.getEmployeeId(), employeeId.toString());
	}
}
