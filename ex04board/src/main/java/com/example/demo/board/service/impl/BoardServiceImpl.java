package com.example.demo.board.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.board.mapper.BoardMapper;
import com.example.demo.board.service.BoardDTO;
import com.example.demo.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardMapper boardMapper;	
	
	@Override
	public void register(BoardDTO board) {
		boardMapper.insert(board);
	}
	@Override
	public boolean modify(BoardDTO board) {
		return boardMapper.update(board) == 1 ? true : false;
	}
	@Override
	public boolean remove(Long bno) {
		return boardMapper.delete(bno) == 1 ? true : false;
	}
	@Override
	public BoardDTO get(Long bno) {
		return boardMapper.read(bno);
	}
	@Override
	public List<BoardDTO> getList() {
		return boardMapper.getList();
	}
}
