package com.example.demo.customer.service;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.example.demo.customer.entity.Customer;
import com.example.demo.customer.service.CustomerVO;

public interface CustomerService {
	public CustomerVO insert(CustomerVO customerVO);
	public CustomerVO update(CustomerVO customerVO);
	public int delete(Long id);
	public List<CustomerVO> findCustomer(CustomerVO customerVO);
}