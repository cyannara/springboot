package com.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:spring/datasource-context.xml")
public class BoardMapperTest {
	
	@Autowired BoardMapper boardMapper;
	
	@Disabled
	@Test
	void 등록() {
		//given
		BoardVO board = new BoardVO();
		board.setTitle("스프링");
		board.setContent("등록테스트");
		board.setWriter("user03");
		//when
		int result = boardMapper.insert(board);
		long bno = board.getBno();
		System.out.println(bno);
		//then
		//등록된 번호로 단건조회
		assertEquals(result, 1);
	}
		

	@Disabled
	@Test
	void 전체조회() {
		//given
		
		//when
		List<BoardVO> list = boardMapper.getList();
		
		//then
		assertEquals(list.get(0).getWriter(), "user00");
		
		for(BoardVO board  : list) {
			System.out.println(board.getBno() +
					" : " + board.getTitle());
			for(ReplyVO reply  : board.getReply()) {
				System.out.println(reply.getReply());
			}
		}
	}
	
	@Disabled
	@Test
	void 삭제() {
		//given
		Long bno = 1l;
		
		//when
		int result = boardMapper.delete(bno);
		
		//then
		assertEquals(result, 1);
	}
	
	@Test
	void 검색() {
		BoardVO board = BoardVO.builder()
		.title("test")
		//.writer("user00")
		.build();
		List<BoardVO> list = boardMapper.getListByWriter(board);
	}
}
