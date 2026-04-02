package com.example.demo.customer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String zipcode;
	private String address;
	private String detail_address;
	
	
	//private Long customer_id;
	
	//@ManyToOne
	//@JoinColumn(name = "customer_id")
	//private Customer customer;

	@Builder
	public Address(String zipcode, String address, String detail_address, Customer customer) {
		this.zipcode = zipcode;
		this.address = address;
		this.detail_address = detail_address;
		this.customer = customer;
	}
}
