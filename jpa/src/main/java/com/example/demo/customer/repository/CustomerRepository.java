package com.example.demo.customer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.customer.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findByFirstName(String name);
	List<Customer> findByLastName(String name);
}
