package com.example.demo.hr.mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.hr.service.EmployeesDTO;
import com.example.demo.hr.service.EmployeesSearchDTO;

public interface EmployeesMapper {
	
	List<Map<String,Object>> findAllMap();
	List<EmployeesDTO> findAll();
	List<EmployeesDTO> findAllOrderBy(
			@Param("order")String order, 
			@Param("dir") String dir);
	int SelectAllCount();
	
	List<EmployeesDTO> findAll(EmployeesSearchDTO search);
	List<EmployeesDTO> findAll(
			@Param("employee")EmployeesDTO dto, 
			@Param("search") EmployeesSearchDTO search);
	
	List<EmployeesDTO> findBydeptAndName(
			@Param("departmentId") Long departmentId, 
			@Param("firstName") String firstName);
	
	Optional<EmployeesDTO> findById(Integer employeeId);
	
	@Select("select * from employees where email = #{email}")
	Optional<EmployeesDTO> findById(String email);
	
	int insert(EmployeesDTO dto);
	int update(EmployeesDTO dto);
	int deleteById(Integer employeeId);
	int deleteAll();

}
