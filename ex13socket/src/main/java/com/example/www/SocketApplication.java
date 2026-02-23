package com.example.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@MapperScan(basePackages = "com.example.www.**.mapper")
public class SocketApplication {  

	public static void main(String[] args) {
		SpringApplication.run(MybatisApplication.class, args);
	}

}
