package com.example.demo.board.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.board.mapper.ReplyMapper;
import com.example.demo.board.service.ReplyDTO;
import com.example.demo.board.service.ReplyPageDTO;
import com.example.demo.board.service.ReplySearchDTO;
import com.example.demo.board.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

  
  private final ReplyMapper mapper;
  
  @Override
  public boolean register(ReplyDTO vo) {
    return mapper.insert(vo) == 1 ? true : false;

  }

  @Override
  public boolean modify(ReplyDTO vo) {
    return mapper.update(vo) == 1 ? true : false;

  }

  @Override
  public boolean remove(Long rno) {
    return mapper.delete(rno) == 1;

  }
  
  @Override
  public ReplyDTO get(Long rno) {
	  return mapper.read(rno);
	  
  }

  
  @Override
  public ReplyPageDTO getList(ReplySearchDTO replySearch, Long bno) {
       
    return new ReplyPageDTO(
        mapper.getCountByBno(bno), 
        mapper.getList(replySearch, bno));
  }


}

