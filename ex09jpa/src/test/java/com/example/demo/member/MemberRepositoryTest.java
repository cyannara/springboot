package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberRepositoryTest {
	@Autowired MemberRepository memberRepository;
	@Autowired TeamRepository teamRepository;
	
	@Commit
	@Transactional
	//@Test  //롤백
	public void 등록() {
		//팀등록
		Team team = new Team();
		team.setName("개발팀");
		teamRepository.save(team);
		
		//맴버등록
		Member member = new Member();
		member.setName("김유신");
		member.setAge(20);
		member.setTeam(team);
		memberRepository.save(member);
		
		//맴버등록
		Member member2 = new Member();
		member2.setName("이순신");
		member2.setAge(20);
		member2.setTeam(team);
		memberRepository.save(member2);
	}
	
	//@Test
	public void 맴버_조회() {
		Member member = memberRepository.findById(1L).get();
		System.out.println(member.getName());
		System.out.println(member.getTeam().getName());
	}
	//@Test
	public void 팀_조회() {
		Team team = teamRepository.findById(1L).get();
		System.out.println(team.getName());
		team.getMembers().forEach(mem -> System.out.println(""+mem.getName()) );
	}
	
	@Test
	public void 맴버이름_검색() {
		List<Member> list = memberRepository.findByname("이순신");
		System.out.println(list.get(0).getName());
	}
}
