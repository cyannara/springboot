package com.yedam.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@Value("${upload.path}")
	String path;
	
	@GetMapping("/")
	public String main(Model model) {
		model.addAttribute("uploadpath",path); 
		return "main";
	}
}
