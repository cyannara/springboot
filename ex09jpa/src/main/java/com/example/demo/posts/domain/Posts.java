package com.example.demo.posts.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * Entity 클래스는 Setter를 만들지 않음
 * 생성자 또는 builder 를 이용하여 값을 변경
 */

@Getter   
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length = 500, nullable = false)
	private String title;
	
	@Column(columnDefinition = "clob", nullable = false)
	private String content;
	
	private String author;
	
	@OneToMany(mappedBy = "posts", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@OrderBy("id asc") // 댓글 정렬    
	private List<Comment> comments;
	
	
	@Builder
	public Posts(String title, String content, String author) {
		this.title = title;
		this.content = content;
		this.author = author;
	}

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
