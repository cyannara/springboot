package com.example.demo.board.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplySearchDTO {
	
	int page;
	int amount;
	
	public int getStart() {
		return (page-1)*amount +1;
	}
	
	public int getEnd() {
		return page*amount;
	}
}
