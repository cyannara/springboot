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
public class CommonCode {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long code;
    private String codename;
}
