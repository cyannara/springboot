package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository 
    extends 
    JpaRepository<Team, Long> {

}
