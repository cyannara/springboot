package com.example.demo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.service.EmployeesDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class EmployeeDAO {

	private final SqlSessionTemplate sqlSession;
	
	public List<EmployeesDto> findAll() {
		return sqlSession.selectList("Employees.findAll");
	}
	//com.example.demo.mapper.EmployeesMapper
	public EmployeesDto findById(Long id) {
		return sqlSession.selectOne("Employees.findById", id);
	}
	
	public List<EmployeesDto> findBydeptAndName(Long departmentId, 
			                             String firstName){
		Map<String, Object> map = new HashMap<>();
		map.put("departmentId", departmentId);
		map.put("firstName", firstName);
		return sqlSession.selectList("Employees.findBydeptAndName", map);
	}
	
	public int insert(EmployeesDto dto) {
		return sqlSession.insert("Employees.insert", dto);
	}
	
	public int update(EmployeesDto dto) {
		return sqlSession.update("Employees.update", dto);
	}
	
	public int delete(Integer employeeId) {
		return sqlSession.delete("Employees.delete", employeeId);
	}
}
