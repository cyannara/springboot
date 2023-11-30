package com.example.demo.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.customer.mapper.CustomerMapper;
import com.example.demo.customer.repository.Customer;
import com.example.demo.customer.repository.CustomerRepository;
import com.example.demo.customer.service.CustomerService;
import com.example.demo.customer.service.CustomerVO;

@Service
public class CustomerServiceImpl implements CustomerService{


	@Autowired CustomerRepository customerRepository;
	@Autowired CustomerMapper customerMapper;
	
	@Override
	public Customer insert(CustomerVO CustomerVO) {
		return customerRepository.save(null);
	}

	@Override
	public int update(CustomerVO CustomerVO) {
		return 0;
	}

	@Override
	public int delete(Long id) {
		return 0;
	}

	@Override
	public List<CustomerVO> findCustomer(CustomerVO CustomerVO) {
		return null;
	}

}
