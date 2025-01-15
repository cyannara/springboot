package com.example.demo.insa.service;

import java.util.List;

public interface DeptService {
	DeptDTO read(Long bno);
	List<DeptDTO> getList(DeptSearchDTO searchDTO);
}
