package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@SpringBootTest
public class MemberEntityTest {

	@Autowired EntityManager em;
	
	@Commit
	@Transactional
	@Test
	public void 회원_저장() {
	    Member member = new Member();
	    member.setName("홍길동");
	    em.persist(member);
	}
}
