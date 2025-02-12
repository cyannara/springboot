package com.example.demo.insa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.insa.mapper.DeptMapper;
import com.example.demo.insa.service.DeptDTO;
import com.example.demo.insa.service.DeptSearchDTO;
import com.example.demo.insa.service.DeptService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DeptServiceImpl implements DeptService{

	public final DeptMapper deptMapper;
	
	@Override
	public DeptDTO read(Long bno) {
		return deptMapper.read(bno);
	}

	@Override
	public List<DeptDTO> getList(DeptSearchDTO searchDTO) {
		return deptMapper.getList(searchDTO);
	}

}
