package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends 
      JpaRepository<Member, Long>{

	//이름검색
	@Query("select m from Member m where m.name = ?1 order by name asc")
	List<Member> findByname(String name);
}
