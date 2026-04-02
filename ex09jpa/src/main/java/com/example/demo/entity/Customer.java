package com.yedam.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private Long status;

    protected Customer() {}

    public Customer(String firstName, String lastName, Long status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

}