package com.example.demo.hr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.hr.mapper.EmployeesMapper;
import com.example.demo.hr.service.EmployeesDTO;

@SpringBootTest
public class EmployeeMapperTest {

@Autowired EmployeesMapper employeesMapper;
	
	@Test
	public void list() {
		List<EmployeesDTO> list = employeesMapper.findAll();
		System.out.println(list.get(0));
	}
	
	@Test
	 @DisplayName("사원번호로조회")
	public void find() {
		//given
		Integer id = 100;

		//when
		EmployeesDTO map = employeesMapper.findById(id).get();
		
		//then
		assertEquals(map.getLastName().toString(), "King");
		long bno = 5L;
		assertEquals(bno, 5L);  
	}
	
	@Test
	public void findBydeptAndName() {
		//given
		Long departmentId = null;
		String firstName = null;
		//when
		List<EmployeesDTO> list = employeesMapper.findBydeptAndName(departmentId, firstName);
		//then
		System.out.println(list);
	}
}
