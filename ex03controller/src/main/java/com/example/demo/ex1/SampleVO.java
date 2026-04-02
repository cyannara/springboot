package com.example.demo.ex1;

import java.util.List;

import lombok.Data;

@Data
public class SampleVO {
	private String name;
	private int age;
	
	private List<String> hobby;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth;
}
