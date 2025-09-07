package com.example.demo.customer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.customer.domain.Customer;
import com.example.demo.customer.domain.CustomerRepository;


@Controller  //Controller, ResponseBody
public class CustomerController {

	@Autowired CustomerRepository repo;
	
	@ResponseBody
	@GetMapping("/findcustomer")
	public Iterable<Customer> findcustomer(){
		return repo.findAll();
	}
	
	@GetMapping("/customer")
	public String findall(Model model){
		model.addAttribute("customer", repo.findAll());
		return "customer";
	}
}