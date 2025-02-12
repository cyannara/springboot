package com.example.demo.board.service;

import java.util.List;

public interface BoardService {

	public void register(BoardDTO board);

	public boolean modify(BoardDTO board);

	public boolean remove(Long bno);

	public BoardDTO get(Long bno);

	public List<BoardDTO> getList(BoardSearchDTO searchDTO);

	public int getCount(BoardSearchDTO searchDTO);
}
