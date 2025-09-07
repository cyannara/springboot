package com.example.demo.customer.domain;

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> ,QuerydslPredicateExecutor<Customer>{

	List<Customer> findByFirstName(String name);

	List<Customer> findByLastName(String name);

	Customer findById(long id);

	List<Customer> findByLastNameOrFirstName(String lastName, String firstName);

}
