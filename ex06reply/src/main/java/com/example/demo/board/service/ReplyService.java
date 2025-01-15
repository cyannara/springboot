package com.example.demo.board.service;

public interface ReplyService {

	public boolean register(ReplyDTO vo);

	public boolean modify(ReplyDTO vo);

	public boolean remove(Long rno);

	public ReplyDTO get(Long rno);

	public ReplyPageDTO getList(ReplySearchDTO replySearch, Long bno);
	
}
