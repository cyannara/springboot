package com.example.demo.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Customer;
import com.example.demo.domain.CustomerRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class CustomerRepositoryTest {

	@Autowired
	CustomerRepository customerRepository;

	//단위테스트가 끝날때 마다 수행
	@AfterEach
	void cleanup() {
		customerRepository.deleteAll();
	}
	
	@Test
	public void 고객_조회() {
		//given
		String name = "홍길동";
		String phone = "011";
		
		Customer customer = Customer.builder().name(name).phone(phone).build();
		customerRepository.save(customer);
		
		//when
		List<Customer> customerList = (List<Customer>) customerRepository.findAll();
		
		//then
		assertEquals(customerList.get(0).getName(), name);
		log.info("조회된 고객 이름: {}", customerList.get(0).getName());
	}

	@Test
	public void 고객_수정() {
		// given - 초기 데이터 저장
		String name = "홍길동";
		String phone = "011";		
		Customer saved = customerRepository.save(Customer.builder().name(name).phone(phone).build());
		
		// when - 고객 정보 수정
		Customer customer = customerRepository.findById(saved.getId()).orElse(new Customer());
		customer.updateName("둘리");
		Customer updated = customerRepository.save(customer);
	
		//then
		assertEquals(updated.getName(), customer.getName());
		log.info("수정된 고객 이름: {}", updated.getName());
	}
}
