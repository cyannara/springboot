package com.example.demo.board.service;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

  private Long rno;
  private Long bno;

  private String reply;
  private String replyer;
  private Date replyDate;
  private Date updateDate;

}
