package com.example.demo.board.service;

import lombok.Data;

@Data
public class BoardSearchDTO {
	int start;
	int end;
	
	int pageUnit;
	String type;
	String keyword;
	
	public String[] getTypeArr() {
		return type == null ? new String[] {} : type.split("");
	}
}
