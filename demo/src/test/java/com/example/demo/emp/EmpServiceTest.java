package com.example.demo.emp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.emp.service.EmpService;

@SpringBootTest
public class EmpServiceTest {
	@Autowired EmpService empService;
	
	@Test
	public void 리스트페이지조회() {
		//given
		EmpVO evo = new EmpVO();
		SearchVO svo = new SearchVO();
		svo.setStart(1);
		svo.setEnd(10);
		//when
		Map<String,Object> map = empService.getEmpList(evo, svo);
		//then
		System.out.println(map.get("count"));
		assertThat(map.get("count")).isNotEqualTo(0);
	}
}
