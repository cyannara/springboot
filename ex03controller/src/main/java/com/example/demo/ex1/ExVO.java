package com.example.demo.ex1;

import java.util.List;

import lombok.Data;

@Data
public class ExVO {
	private String name;
	private int age;
	
	private List<String> hobby;
}
