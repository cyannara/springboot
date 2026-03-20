package com.yedam.app.entity;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired CustomerRepository repository;
	
	public List<Customer> findByStatus(long status){
		return repository.findByStatus(1L);
	}
	
	public  List<Customer> findAllSpec( String firstName,String lastName, Long status){
		return repository.findAll(
				CustomerSpecification.getStatus(status)
				.and(CustomerSpecification.getfirstName(firstName))
				.and(CustomerSpecification.getLastName(lastName))
				);
	};
	public  Page<Customer> findAllApecAndPage( String firstName,String lastName, Long status, Pageable pageable){
		return repository.findAll(
				CustomerSpecification.getStatus(status)
				.and(CustomerSpecification.getfirstName(firstName))
				.and(CustomerSpecification.getLastName(lastName)),
				pageable
				);
	};
	
	
	public  Iterable<Customer> searchJpql( String firstName,
							    	     Long status){
		return repository.searchJpql(firstName, status);
	};
	
	public  List<CustomerResponseDTO> searchJpqlDto( String firstName,
			Long status){
		return repository.searchJpqlDto(firstName, status);
	};
	

}
