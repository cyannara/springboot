package com.example.demo.customer.service;

import com.example.demo.customer.repository.Customer;

import lombok.Data;

@Data
public class CustomerVO {

  private Long id;
  private String firstName;
  private String lastName;
  
  public CustomerVO() {}
  public CustomerVO(Customer customer) {
	  this.id = customer.getId();
	  this.firstName = customer.getFirstName();
	  this.lastName = customer.getLastName();
  }
  
}