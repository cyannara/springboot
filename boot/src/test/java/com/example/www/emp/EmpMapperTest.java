package com.example.www.emp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.www.emp.mapper.EmpMapper;
import com.example.www.emp.service.EmpVO;

@SpringBootTest
public class EmpMapperTest {

	@Autowired EmpMapper mapper;
	
	@Test
	public void getEmpList() {
		System.out.println("111");
		List<EmpVO> list = mapper.getEmpList(null);
		assertTrue(list.size()>0);
	}
}
