package com.exam;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class EmpService {

	final EmpRepository empRepository;
	final AuthRepository authRepository;
	
	public void register() {
		empRepository.insert();
		authRepository.insert();
	}
}
