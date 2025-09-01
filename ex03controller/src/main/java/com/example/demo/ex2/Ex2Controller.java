package com.example.demo.ex2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class Ex2Controller {
	
	//Logger log = LoggerFactory.getLogger(SampleController.class);
		
	@RequestMapping("/ex5")
	public String ex3( CodeListVO list ) {
		log.info(list);
		return "sample";
	}
	

}
