package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional // 테스트가 끝나면 롤백됨
class MemberRepository____Test {

    @Autowired
    MemberRepository____ memberRepository;
    
    @Autowired
    EntityManager em;

    //@Commit
    //@Transactional
    //@Test
    void 회원저장_조회() {
        // given
        Member member = new Member();
        member.setName("홍길동");
        member.setAge(20);

        // when
        Long id = memberRepository.save(member);
        Member found = memberRepository.find(id);

        // then
        assertEquals(found.getName(), "홍길동");
        assertEquals(found.getAge(),20);
    }
    
    @Commit
    @Transactional
    //@Test
    void 회원_팀_저장_조회() {
	    Team team = new Team();
	    team.setName("개발팀");
	    em.persist(team);
	
	    Member member = new Member();
	    member.setName("홍길동");
	    member.setTeam(team);
	    em.persist(member);
	    
	    //맴버 조회
	    Member found = em.find(Member.class, member.getId());
	    System.out.println("회원 이름: " + found.getName());
	    System.out.println("팀 이름: " + found.getTeam().getName()); 
	    
    }
    
    @Test
    public void 팀_조회() {
    	System.out.println("========팀조회=====");
	    Team resultTeam = em.find(Team.class, 1L);
	    System.out.println(resultTeam.getName());
	    resultTeam.getMembers().forEach(mem -> System.out.print(mem.getName() +","));
    }
}
