
package com.example.demo.posts.controller;

import com.example.demo.posts.domain.Posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

	private String title;
	private String contents;
	private String author;
	
	@Builder
	public PostsSaveRequestDto(String title, String content, String author) {
		this.title = title;
		this.contents = content;
		this.author = author;
	}
	
	public Posts toEntity() {
		return Posts.builder()
				.title(title)
				.content(contents)
				.author(author)
				.build();
				
	}
}
