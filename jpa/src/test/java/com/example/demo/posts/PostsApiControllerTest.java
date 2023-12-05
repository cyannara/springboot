package com.example.demo.posts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.posts.controller.PostsSaveRequestDto;
import com.example.demo.posts.domain.Posts;
import com.example.demo.posts.domain.PostsRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private PostsRepository postsRepository;
	
	//@After
	public void tearDowm() throws Exception {
		postsRepository.deleteAll();
	}
	
	@Test
	@Tag("junit5")
	public void Posts_등록() throws Exception{
		//given
		String title = "title";
		String content = "content";
		PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
				.title(title)
				.content(content)
				.author("author")
				.build();
		String url = "http://localhost:" + port + "/api/v1/posts";
		
		//when
		ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
		
		//then
		assertThat(responseEntity.getStatusCode()==HttpStatus.OK);
		
		List<Posts> list = postsRepository.findAll();
		Assertions.assertEquals(list.get(0).getTitle(),title);
		//assertThat(list.get(0).getTitle().equals(title));
		assertThat(list.get(0).getContent().equals(content));
		
		Assertions.assertAll("find all"
				, ()->Assertions.assertEquals(list.get(0).getTitle(),title)
		        , ()->assertThat(list.get(0).getContent().equals(content)) );
	}
}
