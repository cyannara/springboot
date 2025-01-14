package com.example.demo.board.service;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO {
	 private Long bno;
	 
	 @NotBlank
	 private String title;
	 @NotBlank
	 private String content;
	 @NotBlank
	 private String writer;
	 private Date regdate;
}
