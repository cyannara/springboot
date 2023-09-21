package com.example.www.emp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.www.emp.service.EmpService;

@Controller
public class EmpController {
	
	@Autowired	EmpService empService;

	@RequestMapping("/main") 
	public String main(){
		return "main"; 
	}
	
	@RequestMapping("/empList")
	public String empList(Model model) {
		model.addAttribute("empList", empService.getEmpList(null));
		return "empList";
	}
}
