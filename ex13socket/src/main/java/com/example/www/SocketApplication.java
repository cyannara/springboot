package com.example.www;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example.www.**.mapper")
public class SocketApplication {  

	public static void main(String[] args) {
		SpringApplication.run(SocketApplication.class, args);
	}

}
