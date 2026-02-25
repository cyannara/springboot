package com.example.demo.hr;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.hr.dao.EmployeeDAO;
import com.example.demo.hr.service.EmployeesDTO;


@SpringBootTest
public class EmplpyeeDAOTest {

	@Autowired
	EmployeeDAO dao;
	
	@Test
	public void test() {
		//given
		Long employeeId = 100l;
		
		//when
		EmployeesDTO dto =  dao.findById(employeeId);
		System.out.println(dto);
		
		//then
		assertEquals(dto.getEmployeeId(), employeeId.toString());
	}
}
