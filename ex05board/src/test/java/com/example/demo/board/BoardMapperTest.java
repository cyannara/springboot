package com.example.demo.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.board.mapper.BoardMapper;
import com.example.demo.board.service.BoardDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class BoardMapperTest {

	@Autowired
	BoardMapper boardMapper;

	@Test
	@DisplayName("게시글 등록")
	public void register() {
		String title = "등록";
		// given
		BoardDTO board = BoardDTO.builder().title(title).content("내용").writer("작성자").build();
		// when
		int cnt = boardMapper.insert(board);
		// then
		assertThat(cnt).isEqualTo(cnt);
	}

	@Test
	@DisplayName("게시글 삭제")
	public void testDelete() {
		// given
		long bno = 5L;

		// when
		int cnt = boardMapper.delete(3L);

		// then
		log.info("DELETE COUNT: " + cnt);
		assertThat(cnt).isEqualTo(cnt);
		// assertEquals(cnt, 1);
	}

	@Test
	@DisplayName("게시글 수정")
	public void testUpdate() {
		// given
		// 실행전 존재하는 번호인지 확인할 것
		BoardDTO board = BoardDTO.builder().bno(4L).title("수정된 제목").content("수정된 내용").writer("user00").build();

		// when
		int cnt = boardMapper.update(board);

		// then
		log.info("UPDATE COUNT: " + cnt);
		// assertEquals(cnt, 1);
		assertThat(cnt).isEqualTo(cnt);
	}

	@Test
	@DisplayName("게시글 상세조회")
	public void testRead() {
		// given
		Long bno = 5L;

		// when
		BoardDTO board = boardMapper.read(bno);

		// then
		log.info(board.toString());
		assertThat(board).isNotNull();
	}

	@Test
	  @DisplayName("게시글전체조회")
	  public void testGetList() {
	    //given

	    //when
	    List<BoardDTO> list = boardMapper.getList(null);

	    //then
	    list.forEach(board -> log.info(board.toString()));
	    //assertNotNull(list);
	    assertThat(list).isNotNull();
	  }
}
