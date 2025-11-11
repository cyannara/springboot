package com.example.demo.domain;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 20, nullable = false)
	private String name;

	@Column(length = 20, nullable = false, unique = true)
	private String phone;

	@CreatedDate
	private LocalDate rdt;
	
	@LastModifiedDate
	private LocalDate udt;
	
	@Builder
	public Customer(String name, String phone) {
		super();
		this.name = name;
		this.phone = phone;
	}

	public void updateName(String name) {
		this.name = name;
	}
}
