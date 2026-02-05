package com.yedam.app.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yedam.app.member.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {

}
