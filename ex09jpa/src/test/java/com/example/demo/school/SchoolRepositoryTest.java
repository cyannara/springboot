package com.example.demo.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class SchoolRepositoryTest {

	@Autowired SchoolRepository schoolRepository;
	@Autowired StudentRepository studentRepository;


	 @Test
	 @Transactional
	 void manytoOneTest(){

		 School school = new School("예담고등학교");

		 Student s1 = new Student("홍길동");
		 Student s2 = new Student("이몽룡");

		 school.addStudent(s1);
		 school.addStudent(s2);

		 // school 저장 시 students 도 함께 persist 됨 (CascadeType.ALL 덕분에)
		 schoolRepository.save(school);
	      
		 studentRepository.findAll().forEach(std -> {  log.info(std);   });
	 }
	 
}
