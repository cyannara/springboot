package com.example.demo.rest1;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "티켓 정보 DTO") 
@Data
public class Ticket {
	
	@Schema(description = "티켓 고유 ID", example = "12345")
	int tno;
	
	@Schema(description = "소유자", example = "user12")
	String owner;
	
	String grade;
	
	String seatNo;
}
