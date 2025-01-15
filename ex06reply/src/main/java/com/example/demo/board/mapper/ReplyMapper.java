package com.example.demo.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.demo.board.service.ReplyDTO;
import com.example.demo.board.service.ReplySearchDTO;

public interface ReplyMapper {

	public int insert(ReplyDTO reply);

	public int update(ReplyDTO reply);

	public int delete(Long bno);

	public ReplyDTO read(Long bno);

	public List<ReplyDTO> getList(@Param("cri") ReplySearchDTO cri, 
			                                @Param("bno") Long bno);

	public int getCountByBno(Long bno);
}
