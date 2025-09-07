package com.example.demo.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.customer.domain.Address;
import com.example.demo.customer.domain.AddressRepository;
import com.example.demo.customer.domain.Customer;
import com.example.demo.customer.domain.CustomerRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class CustomerRepositoryTest {

	@Autowired CustomerRepository customerRepository;
	@Autowired AddressRepository addressRepository;
	
	//@Test
	void save() {
		customerRepository.save(new Customer("홍","기자"));
		
		Customer customer = customerRepository.findById(1L);
		assertEquals(customer.getFirstName(), "김"); 
	}
	
	//@Test
	void find() {
		List<Customer> cust = customerRepository.findByLastNameOrFirstName("홍", "기자");
		System.out.println(cust);
	}
	
	
	@Test
	void onetoOneCustomerOwnerTest() {
		// given(준비)
		Address addressEntity = Address.builder().zipcode("04411").address("대구").build(); 
		addressRepository.save(addressEntity);
		
		Customer customerentity = Customer.builder()
                                        .firstName("길동")
                                        .address(addressEntity)
                                        .build();
		customerRepository.save(customerentity);

		//when(실행)
		Customer customer = customerRepository.findById(1L);
		log.info(customer.getFirstName()+":"+customer.getAddress().getZipcode());
		
		//then(검증)
		assertEquals("04411", customer.getAddress().getZipcode());
	}

//	 @Test
//	 //@Transactional
//	 void manytoOneTest(){
//	      // save a few customers
//	      Customer cust= new Customer("Jack", "Bauer");
//	      repository.save(cust);
//	      addressRepository.save(Address.builder()
//	    		  .customer_id(1L)
//	                               .zipcode("04411").address("대구").detail_address("중구")
//	                               .build());
//	      addressRepository.save(Address.builder()
//	    		  .customer_id(1L)
//	                               .zipcode("04412").address("대구").detail_address("남구")
//	                               .build());
//	      
//	      
//	      addressRepository.findAll().forEach(addr -> {
//	      log.info(addr.toString());
//	       });
//	 }
	 
//		@Test
//		@Transactional
//		void manytoOneTest2(){
//		      // save a few customers
//		      Customer cust= new Customer("Jack", "Bauer");
//		      repository.save(cust);
//		      addressRepository.save(Address.builder().customer(cust).zipcode("04411").address("대구").detail_address("중구").build());
//		      addressRepository.save(Address.builder().customer(cust).zipcode("04412").address("대구").detail_address("남구").build());
//		      
//		      repository.findByLastName("Bauer").forEach(bauer -> {
//		    	  log.info(bauer.toString());
//			  });
//		      
//		      addressRepository.findAll().forEach(addr -> {
//		    	  log.info(addr.toString());
//			  });
//		}
}
