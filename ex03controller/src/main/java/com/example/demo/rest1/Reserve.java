package com.example.demo.rest1;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Reserve {

	int cnt;

	Date regdate;

	User user;
	
	List<Ticket> list;

}
