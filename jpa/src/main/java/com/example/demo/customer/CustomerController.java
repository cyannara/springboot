package com.example.demo.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController  //Controller, ResponseBody
public class CustomerController {

	@Autowired CustomerRepository repo;
	
	@GetMapping("/customer")
	public Iterable<Customer> findall(){
		return repo.findAll();
	}
}
