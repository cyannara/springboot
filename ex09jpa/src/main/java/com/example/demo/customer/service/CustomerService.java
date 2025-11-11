package com.example.demo.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.customer.domain.Customer;
import com.example.demo.customer.domain.CustomerRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class CustomerService {

	@Autowired CustomerRepository custRepository;
	
	List<Customer> findPaging(int page) {
		
		Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
		/*
		 * //QCustomer qCustomer = QCustomer.customer; String keyword = "name";
		 * 
		 * BooleanBuilder builder = new BooleanBuilder(); BooleanExpression expression =
		 * qCustomer.name.contains(keyword);
		 * builder.and(qCustomer.name.contains(keyword));
		 * 
		 * Page<Customer> result = custRepository.findAll(builder,pageable);
		 */
		return null;
	}
}
