package com.example.demo.hr.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.hr.service.EmployeesDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class EmployeeDAO {

	private final SqlSessionTemplate sqlSession;
	
	public List<EmployeesDTO> selectAll() {
		return sqlSession.selectList("Employees.findAll");
	}
	//com.example.demo.mapper.EmployeesMapper
	public EmployeesDTO findById(Long id) {
		return sqlSession.selectOne("Employees.findById", id);
	}
	
	public List<EmployeesDTO> selectBydeptAndName(Long departmentId, 
			                             String firstName){
		Map<String, Object> map = new HashMap<>();
		map.put("departmentId", departmentId);
		map.put("firstName", firstName);
		// Mapper 메서드에 파라미터가 여러 개 있으면 Map으로 변환합니다.
		return sqlSession.selectList("Employees.findBydeptAndName", map);
	}
	
	public int insert(EmployeesDTO dto) {
		return sqlSession.insert("Employees.insert", dto);
	}
	
	public int update(EmployeesDTO dto) {
		return sqlSession.update("Employees.update", dto);
	}
	
	public int delete(Integer employeeId) {
		return sqlSession.delete("Employees.delete", employeeId);
	}
}
