package com.example.demo.di;

public class Restaurant {
	private Chef chef;
	
	public Restaurant(Chef chef) {
		this.chef = chef;
	}
	
	public Chef getChef() {
		return chef;
	}
}
