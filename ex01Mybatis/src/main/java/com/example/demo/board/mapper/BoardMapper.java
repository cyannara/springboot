package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BoardMapper {

	//등록
	int insert(BoardVO board);
	
	//수정
	int update(BoardVO board);
	
	//삭제
	int delete(Long bno);
	
	//단건조회
	int count();
	
	//전체조회
	List<BoardVO> getList();
	
	//전체조회(검색)
	List<BoardVO> getListByWriter(BoardVO board);
	
	//페이징-전체건수조회
	
}
