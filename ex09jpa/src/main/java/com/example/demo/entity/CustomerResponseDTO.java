package com.yedam.app.entity;

import lombok.Data;

@Data
public class CustomerResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Long status;
    private String statusName;
    
    public CustomerResponseDTO() {}
    
	public CustomerResponseDTO(Long id, String firstName, String lastName, Long status) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
	}
    
    
}
