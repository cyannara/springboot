package com.example.demo.ex3;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

//@Log4j2
@Controller
public class Ex3Controller {
	
	Logger log = LoggerFactory.getLogger(Ex3Controller.class);
	
	
	@PostMapping("/member")
	public String user(@Validated Member member, 
                       BindingResult bindingResult ) {
		if (bindingResult.hasErrors()) {
			return "sample";
		}
		return "sample";
	}
	
	@PostMapping("/file")
	public String file(@RequestPart("file") MultipartFile file) throws IllegalStateException, IOException {
		System.out.println(file.getSize());
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		file.transferTo(new File("c:/upload",file.getOriginalFilename()));
		return "sample";
	}
	
	@RequestMapping("/files")
	public String ex6(EmpVO vo, MultipartFile[] photos) throws IllegalStateException, IOException {
		System.out.println(vo);
		if(photos != null) { 
			for( MultipartFile photo : photos ) {
				if( photo.getSize()>0 ) { 

					File file = new File("d:/upload", photo.getOriginalFilename());
					photo.transferTo(file);

				}
			}
		}
		return "sample";
	}

}
