package com.example.demo.posts.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.demo.posts.domain.Comment;
import com.example.demo.posts.domain.Posts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {
	private Long id;
	private String comment;
	private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
	private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));

//private User user;
	private Posts posts;

	/* Dto -> Entity */
	public Comment toEntity() {
		Comment comments = Comment.builder().id(id).comment(comment).createdDate(createdDate).modifiedDate(modifiedDate)
				//.user(user)
				.posts(posts).build();
		return comments;
	}
}
