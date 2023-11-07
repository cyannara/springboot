package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerRepository;

@SpringBootTest
class JpaApplicationTests {

	@Autowired
	CustomerRepository customerRepo;
	
	@Test 
	void findbyname(){
		List<Customer> list = customerRepo.findByFirstName("이순신");
		System.out.println(list);
		assertEquals("순신", list.get(0).getFirstName());
	}
	
	//@Test
	void findall() {
		Iterable<Customer> list =  customerRepo.findAll();
		//list.forEach(cust ->  System.out.println(cust.getName())  );
		
		Iterator<Customer> iter = list.iterator();
		while(iter.hasNext()) {
			Customer cust = iter.next();
			System.out.println( cust.getFirstName() );
		}
	}
	
	//@Test
	void save() {
		Customer cust = new Customer("순신","이");
		Customer result = customerRepo.save(cust);
		assertEquals(cust.getFirstName(), result.getLastName());
	}

}
