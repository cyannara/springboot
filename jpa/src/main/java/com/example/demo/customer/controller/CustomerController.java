package com.example.demo.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.customer.service.CustomerService;
import com.example.demo.customer.service.CustomerVO;


@Controller  //Controller, ResponseBody
public class CustomerController {

	@Autowired CustomerService service;
	

	@ResponseBody
	@GetMapping("/findcustomer")
	public List<CustomerVO> findcustomer(CustomerVO vo){
		return service.findCustomer(vo);
	}
	
	@GetMapping("/customer")
	public String findall(Model model){
		model.addAttribute("customer", service.findCustomer(null));
		return "customer";
	}
	
	@PostMapping("/customer")
	public String insert(CustomerVO vo) {
		service.insert(vo);
		return null;
	}
}