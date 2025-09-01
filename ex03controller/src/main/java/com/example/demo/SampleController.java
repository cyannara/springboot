package com.example.demo;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/sample/*")
//@Log4j2
public class SampleController {
	
	Logger log = LoggerFactory.getLogger(SampleController.class);
	
	@RequestMapping("")
	public String basic() {
		return "sample";
	}
	
	//@RequestMapping( value="basic", method = { RequestMethod.GET, RequestMethod.POST } )
	@GetMapping("basic")
	public String basicGet() {
		return "sample";
	}
	
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		log.info("dto" + dto);
		return "sample";
	}
	
	@PostMapping("/file")
	public String file(@RequestPart("file") MultipartFile file) throws IllegalStateException, IOException {
		System.out.println(file.getSize());
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		file.transferTo(new File("c:/upload",file.getOriginalFilename()));
		System.out.println();
		return "home";
	}
}
