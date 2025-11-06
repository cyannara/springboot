package com.example.demo.posts.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.posts.domain.Comment;
import com.example.demo.posts.domain.CommentRepository;
import com.example.demo.posts.domain.Posts;
import com.example.demo.posts.domain.PostsRepository;
import com.example.demo.posts.dto.CommentRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
//private final UserRepository userRepository;
	private final PostsRepository postsRepository;

	/* CREATE */
	@Transactional
	public Long commentSave(String nickname, Long id, CommentRequestDto dto) {
//User user = userRepository.findByNickname(nickname);
		Posts posts = postsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + id));
//dto.setUser(user);
		dto.setPosts(posts);
		Comment comment = dto.toEntity();
		commentRepository.save(comment);
		return dto.getId();
	}
}
