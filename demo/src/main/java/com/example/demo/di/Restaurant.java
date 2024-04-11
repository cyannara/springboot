package com.example.demo.di;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
@Scope("prototype")  //singletone, prototype, request, session
public class Restaurant {
	final private Chef chef;
}
