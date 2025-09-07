package com.example.demo.posts.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.example.demo.customer.domain.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "comments")
@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "clob", nullable = false)
	private String comment; // 댓글 내용     
	
	@Column(name = "created_date")
	@CreatedDate
	private String createdDate;
	
	@Column(name = "modified_date")
	@LastModifiedDate
	private String modifiedDate;
	
	@ManyToOne
	@JoinColumn(name = "posts_id")
	private Posts posts;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Customer customer; // 작성자
}
