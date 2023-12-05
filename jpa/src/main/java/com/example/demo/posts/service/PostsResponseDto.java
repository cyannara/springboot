package com.example.demo.posts.service;

import com.example.demo.posts.domain.Posts;

import lombok.Getter;

@Getter
public class PostsResponseDto {
	private Long id;
	private String title;
	private String contents;
	private String author;
	
	public PostsResponseDto(Posts entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.contents = entity.getContent();
		this.author = entity.getAuthor();
	}
}
