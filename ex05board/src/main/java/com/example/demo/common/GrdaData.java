package com.example.demo.common;

import java.util.List;

import lombok.Data;

@Data
public class GrdaData<T> {
	List<T> createdRows; 
	List<T> deletedRows; 
	List<T> updatedRows; 

}
