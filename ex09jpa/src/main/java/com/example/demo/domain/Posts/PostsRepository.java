package com.example.demo.domain.Posts;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostsRepository extends JpaRepository<Posts, Long> {

	@Query("SELECT p FROM Posts p ORDER BY p.id DESC")
	List<Posts> findAllDesc();

	// @Query("SELECT new com.yourdomain.ReportResponseDto(r) FROM Report r WHERE
	// (:#{#condition.name} IS NULL OR r.name = :#{#condition.name}) AND
	// (:#{#condition.status} IS NULL OR r.status = :#{#condition.status})")
	@Query("SELECT p FROM Posts p ")
	Page<PostsListResponseDto> findAllPage(Pageable pageable);
}
