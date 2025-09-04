package com.yedam.app;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BoardVO {
	private Integer bno;		//번호
	private String title;		//제목
	private String contents;	//내용
	private String writer;		//작성자
	// java.util.Date : yyyy/MM/dd
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date regdate;		//작성일
	private Date updatedate;	//수정일
	private String attach;		//첨부파일
}
