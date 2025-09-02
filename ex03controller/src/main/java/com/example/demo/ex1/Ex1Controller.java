package com.example.demo.ex1;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.ex3.EmpVO;

/*
 * 방문객의 이름과 나이 입력받기
 * ex1: 커맨드 객체 
 * ex2: @RequestParam
 * ex3: @PathVariable
 */

//@Log4j2
@Controller
public class Ex1Controller {

	Logger log = LoggerFactory.getLogger(Ex1Controller.class);

	/**
	 * 커맨더 객체로 받기
	 * 
	 * @param dto
	 * @return
	 */
	@GetMapping("/ex1")
	//@RequestMapping( value="basic", method = { RequestMethod.GET, RequestMethod.POST } )
	public String ex1(SampleVO dto) {
		log.info("dto" + dto);
		return "sample";
	}

	/**
	 * PathVariable로 파라미터 받기
	 * 
	 * @param username
	 * @param age
	 * @return
	 */
	@RequestMapping("/ex2/{username}/{userage}")
	public String ex2(@PathVariable(name = "username") String username, 
			          @PathVariable(name = "userage") int age) {
		log.info("username:" + username);
		log.info("age:" + age);
		return "sample";
	}

	/**
	 * RequestParam으로 파라미터 받기
	 * 
	 * @param username
	 * @param age
	 * @return
	 */
	@RequestMapping("/ex3") // 커맨드객체 없이 파라미터 localhost:8081/ex4?username=xxx&userage=20
	public String ex3(@RequestParam("username") String username,
			          @RequestParam(name = "userage", required = false, defaultValue = "10") Integer age) {
		log.info("username:" + username);
		log.info("age:" + age);
		return "sample";
	}
	
	@GetMapping("/ex4")
	public String ex4(@RequestParam("username") List<String> hobby ) {
		log.info("hobby:" + hobby);
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
