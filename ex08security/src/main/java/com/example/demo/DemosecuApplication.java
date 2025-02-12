package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example.demo.**.mapper")
public class DemosecuApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemosecuApplication.class, args);
	}

}
