package com.example.demo.emp.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.emp.EmpVO;
import com.example.demo.emp.SearchVO;
public interface EmpService {
	Map<String,Object> getEmpList(EmpVO vo, SearchVO svo);
	EmpVO getEmpInfo(int employeeId);
	int insertEmp(EmpVO empVO);
	int deleteEmp(int employeeId);
	List<Map<String,Object>> getStat();
	
}
