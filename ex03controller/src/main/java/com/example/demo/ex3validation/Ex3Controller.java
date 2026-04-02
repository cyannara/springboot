package com.example.demo.ex3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

/*
 * 1. @Valid는 자바 표준 스펙이고 @Validated는 스프링에서 제공하는 어노테이션이다.
   2. @Validated를 통해 그룹 유효성 검사나 Controller가 아닌 다른 계층에서 유효성 검증 가능
   3. @Valid는 MethodArgumentNotValidException 예외를 @Validated는 ConstraintViolationException 예외를 발생시킨다.
   4. Valid 어노테이션은 주로 request body를 검증하는데 많이 사용
 */

//@Log4j2
@Controller
public class Ex3Controller {
	
	Logger log = LoggerFactory.getLogger(Ex3Controller.class);
	
	@GetMapping("/member")
	public String showForm(Member member) {
		return "member";
	}
	
	@PostMapping("/member")
	public String user(@Valid Member member, 
                       BindingResult bindingResult ) {
		if (bindingResult.hasErrors()) {
			return "member";
		}
		return "redirect:/ex1";
	}


}
