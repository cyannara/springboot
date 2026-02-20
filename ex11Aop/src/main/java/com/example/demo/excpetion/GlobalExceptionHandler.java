package com.example.demo.excpetion;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {
//
//	// 특정 예외처리
//	@ExceptionHandler(ArithmeticException.class)
//	public String handleMaxSizeException(ArithmeticException ex) {
//		return "error";
//	}
//
//	//특정 예외처리
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public String handleMaxSizeException(MaxUploadSizeExceededException ex, Model model) {
//			  model.addAttribute("msg", "업로드 가능한 파일 크기를 초과했습니다.");
//        return "error";
//    }
//	
	// 기본 예외 처리
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception e, Model model) {
		System.out.println("Error Message " + e.getMessage());
        model.addAttribute("message", e.getMessage());
        model.addAttribute("status", 500);
		return "error";
	}
}
