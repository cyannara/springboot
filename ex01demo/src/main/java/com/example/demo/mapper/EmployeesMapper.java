package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface EmployeesMapper {
	List<EmployeesDto>  findAll();
	EmployeesDto findById(Long id);
	List<EmployeesDto> findBydeptAndName(@Param("departmentId") Long departmentId, 
			                                   @Param("firstName") String firstName);
}
