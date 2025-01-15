package com.example.demo.board.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyPageDTO {

  private int replyCnt;
  private List<ReplyDTO> list;
}
