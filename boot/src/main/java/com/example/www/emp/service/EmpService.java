package com.example.www.emp.service;

import java.util.List;

import com.example.www.emp.service.EmpVO;

public interface EmpService {
	public EmpVO getEmp(EmpVO empVO);
	public List<EmpVO> getEmpList(EmpVO empVO);
	public void empInsert(EmpVO empVO);
}