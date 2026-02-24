package com.example.www.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AlamVO {
	private String cmd;
	private String name;
	private String msg;
}
