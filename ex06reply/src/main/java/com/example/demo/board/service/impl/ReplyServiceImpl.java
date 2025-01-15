package com.example.demo.board.service.impl;

import java.util.List;

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
  public int register(ReplyDTO vo) {

    log.info("register......" + vo);

    return mapper.insert(vo);

  }

  @Override
  public ReplyDTO get(Long rno) {

    log.info("get......" + rno);

    return mapper.read(rno);

  }

  @Override
  public int modify(ReplyDTO vo) {

    log.info("modify......" + vo);

    return mapper.update(vo);

  }

  @Override
  public int remove(Long rno) {

    log.info("remove...." + rno);

    return mapper.delete(rno);

  }

  @Override
  public List<ReplyDTO> getList(ReplySearchDTO replySearch, Long bno) {

    log.info("get Reply List of a Board " + bno);

    return mapper.getList(replySearch, bno);

  }
  
  @Override
  public ReplyPageDTO getListPage(ReplySearchDTO replySearch, Long bno) {
       
    return new ReplyPageDTO(
        mapper.getCountByBno(bno), 
        mapper.getList(replySearch, bno));
  }


}

