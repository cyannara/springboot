package com.example.demo.customer.domain;

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> ,QuerydslPredicateExecutor<Customer>{

	List<Customer> findByFirstName(String name);

	List<Customer> findByLastName(String name);

	Customer findById(long id);

	List<Customer> findByLastNameOrFirstName(String lastName, String firstName);

	List<Customer> findByNameLike(String name);
	//특정단어가 포함된 이메일 조회
	//____
	//특정단어가 이름 또는 이메일에 포함된 경우  (Or)
	
	//@Query(value = "select * from customer where phone = ?1 or name = ?2", nativeQuery = true)
	@Query(value = "select * from customer where phone = :phone or name = :name", nativeQuery = true)
	List<Object[]> findAllNative(@Param("phone") String phone, @Param("name")String name);
	
	@Query(value = "select * from customer where phone = :phone or name = :name", nativeQuery = true)
	List<CustomerNative> findAllNativeVO(@Param("phone") String phone, @Param("name")String name);
	
	//JPQL
	@Query("select c from Customer c order by id DESC")
	List<Customer> findAllQuery();
}
