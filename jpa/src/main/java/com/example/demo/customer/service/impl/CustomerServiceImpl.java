package com.example.demo.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.customer.entity.Customer;
import com.example.demo.customer.mapper.CustomerMapper;
import com.example.demo.customer.repository.CustomerRepository;
import com.example.demo.customer.service.CustomerService;
import com.example.demo.customer.service.CustomerVO;

@Service
public class CustomerServiceImpl implements CustomerService{


	@Autowired CustomerRepository customerRepository;
	@Autowired CustomerMapper customerMapper;
	
	@Override
	public CustomerVO insert(CustomerVO customerVO) {
		Customer customer = customerRepository.save(customerVO.toEntity());
		return new CustomerVO(customer);
	}

	@Override
	public CustomerVO update(CustomerVO customerVO) {
		Customer customer = customerRepository.save(customerVO.toEntity());
		return new CustomerVO(customer);
	}

	@Override
	public int delete(Long id) {
		customerRepository.deleteById(id);
		return 1;
	}

	@Override
	public List<CustomerVO> findCustomer(CustomerVO CustomerVO) {
		return customerMapper.getCustomer(CustomerVO);
	}

}
