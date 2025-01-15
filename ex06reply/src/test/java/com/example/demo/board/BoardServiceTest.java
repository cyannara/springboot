package com.example.demo.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.board.service.BoardDTO;
import com.example.demo.board.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class BoardServiceTest {

	@Autowired
	BoardService boardService;

	@Test
	@DisplayName("게시글 수정")
	public void update() {
		// given
		// 실행전 존재하는 번호인지 확인할 것
		BoardDTO board = BoardDTO.builder()
				   .bno(4L)
				   .title("서비스수정")
				   .content("서비스 내용")
				   .writer("user00")
				   .build();

		// when
		boolean result = boardService.modify(board);

		// then
		assertThat(result).isEqualTo(true);
	}

}
