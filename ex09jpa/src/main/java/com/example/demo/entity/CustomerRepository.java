package com.yedam.app.entity;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends 
			JpaRepository<Customer, Long>,
			JpaSpecificationExecutor<Customer> {

    List<Customer> findByLastName(String lastName);

    Customer findById(long id);
    
    List<Customer> findByStatus(long status);
    
    @Query("""
    	    SELECT c
    	    FROM Customer c
    	    WHERE (:firstName IS NULL OR c.firstName LIKE %:firstName%)
    	      AND (:status IS NULL OR c.status = :status)
    	""")
    Iterable<Customer> searchJpql(
    	    @Param("firstName") String firstName,
    	    @Param("status") Long status
    	);
    
    @Query("""
    	    SELECT new com.yedam.app.entity.CustomerResponseDTO(
    	        c.id,
            c.firstName,
            c.lastName,
            c.status)
    	    FROM Customer c
    	    WHERE (:firstName IS NULL OR c.firstName LIKE %:firstName%)
    	      AND (:status IS NULL OR c.status = :status)
    	""")
    List<CustomerResponseDTO> searchJpqlDto(
    	    @Param("firstName") String firstName,
    	    @Param("status") Long status
    	);

}
