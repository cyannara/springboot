package com.example.demo.domain;


import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.posts.domain.Posts;
import com.example.demo.posts.domain.PostsRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

	@Autowired
	PostsRepository postsRepository;
	
	@After
	public void cleanup() {
		postsRepository.deleteAll();
	}
	
	@Test
	public void 게시글저장_불러오기() {
		//given
		String title = "테스트 게시글";
		String content = "테스트 본문";
		postsRepository.save(Posts.builder()
				.title(title)
				.content(content)
				.author("test@naver.com")
				.build());
		
		//when
		List<Posts> postsList = postsRepository.findAll();
		System.out.println(postsList);
		
		Posts posts = postsList.get(0);
		assertEquals(posts.getTitle(),title);
		assertEquals(posts.getContent(),content);
		//assertThat(posts.getContent()).isEqualTo(content);
		
	}
	
	
}
