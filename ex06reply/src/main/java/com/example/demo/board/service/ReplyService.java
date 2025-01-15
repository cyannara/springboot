package com.example.demo.board.service;

import java.util.List;

public interface ReplyService {

	public int register(ReplyDTO vo);

	public ReplyDTO get(Long rno);

	public int modify(ReplyDTO vo);

	public int remove(Long rno);

	public List<ReplyDTO> getList(ReplySearchDTO cri, Long bno);
	
	public ReplyPageDTO getListPage(ReplySearchDTO cri, Long bno);

}
