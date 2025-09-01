package com.example.demo.rest1;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
	private Integer mno;
	
	private String firstName;
	
	private String lastName;

	private Date regdate;
}
