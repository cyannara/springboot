package com.example.demo.common;

import java.util.List;

import lombok.Data;

@Data
public class GridData<T> {
	List<T> createdRows;
	List<T> updatedRows;
	List<T> deletedRows;
}
