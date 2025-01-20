package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.mapper.EmployeesDto;
import com.example.demo.mapper.EmployeesMapper;

@SpringBootTest
public class EmployeeMapperTest {

	@Autowired EmployeesMapper employeesMapper;
	
	@Test
	public void list() {
		List<EmployeesDto> list = employeesMapper.findAll();
		System.out.println(list.get(0));
	}
	
	@Test
	public void find() {
		//given
		Long id = 100L;

		//when
		EmployeesDto map = employeesMapper.findById(id);
		
		//then
		assertEquals(map.getLastName().toString(), "King");		
	}
	
	@Test
	public void findBydeptAndName() {
		//given
		Long departmentId = null;
		String firstName = null;
		//when
		List<EmployeesDto> list = employeesMapper.findBydeptAndName(departmentId, firstName);
		//then
		System.out.println(list);
	}
}
