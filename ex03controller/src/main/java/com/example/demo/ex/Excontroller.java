package com.example.demo.ex;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller  
public class Excontroller {

	@RequestMapping("/ex6")
	public String ex6(EmpVO vo, MultipartFile[] photos) throws IllegalStateException, IOException {
		System.out.println(vo);
		if(photos != null) { 
			for( MultipartFile photo : photos ) {
				if( photo.getSize()>0 ) { 
					//파일 생성
					File file = new File("d:/upload", photo.getOriginalFilename());
					//파일 저장
					photo.transferTo(file);
					
					System.out.println("파일명:" + photo.getOriginalFilename());
					System.out.println("파일크기:" + photo.getSize());
				}
			}
		}
		return "index";
	}
	
	@RequestMapping("/ex5")  //커맨드객체 없이 파라미터  localhost:8081/ex4?username=xxx&userage=20
	public String ex5(String username, 
			          @RequestParam(name="userage", required=false, defaultValue="10") Integer age) {
		System.out.println("username:" + username);
		System.out.println("age:" + age);
		return "index";
	}
	
	@RequestMapping("/ex4/{username}/{userage}")
	public String ex4(@PathVariable String username, @PathVariable(name = "userage") int age) {
		System.out.println("username:" + username);
		System.out.println("age:" + age);
		return "index";
	}

	
	@RequestMapping("/ex3")
	public String ex3( ListCodeVO vo ) {
		System.out.println(vo);
		return "index";
	}
	
	@RequestMapping("/ex2")
	public String ex2( EX1VO vo ) {
		System.out.println(vo);
		return "index";
	}
}
