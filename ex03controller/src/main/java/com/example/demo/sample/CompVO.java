package com.example.demo.sample;

import java.util.Date;
import java.util.List;

import lombok.Data;
@Data
public class CompVO {
	  List<Ticket> list;
	  SampleVO sample;
	  int cnt;
	  Date regdate;
}
