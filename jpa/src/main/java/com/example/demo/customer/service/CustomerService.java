package com.example.demo.customer.service;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.example.demo.customer.repository.Customer;
import com.example.demo.customer.service.CustomerVO;

public interface CustomerService {
	public Customer insert(CustomerVO CustomerVO);
	public int update(CustomerVO CustomerVO);
	public int delete(Long id);
	public List<CustomerVO> findCustomer(CustomerVO CustomerVO);
}