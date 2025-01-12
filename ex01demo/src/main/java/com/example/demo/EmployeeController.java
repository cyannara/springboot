package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.mapper.EmployeesMapper;

@Controller
public class EmployeeController {
	
	@Autowired
	EmployeesMapper employeesMapper;
	
	@GetMapping("/emp")
	public String list(Model model){
		model.addAttribute("empList", employeesMapper.findAll());
		return "empList";
	}
	
	@ResponseBody
	@GetMapping("/")
	public String hello() {
		return "hello ~~!!!!~";
	}
}
