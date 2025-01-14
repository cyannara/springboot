package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class UserValidationTest {

	private Validator validator;

	@BeforeEach
	void setUp() {
	    validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	@Test
	void test() {
		User user = new User();
		user.setName("test-name");
		user.setWorking(true);
		user.setAboutMe("test-about-me");
		user.setAge(24);
		user.setEmail("test.baeldung.ut");
		user.setPhone("011-333-1111");
		user.setEnddate(new Date(2025,2,10));
		user.setStartdate(new Date(2025,3,11));
		
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		violations.forEach(err->log.debug(err.getPropertyPath().toString() + ": " + err.getMessage()));
		assertTrue(violations.isEmpty());
	}
}
