package com.yedam.app.entity;

import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {
    public static Specification<Customer> visibleTo(boolean isAdmin) {
        return (root, query, cb) -> {
            if (isAdmin) {
                return cb.conjunction(); // 조건 없음
            }
            return cb.equal(root.get("status"), 1);
        };
    }
    
    public static Specification<Customer> getLastName(String lastName) {
        return (root, query, cb) -> {
            if (lastName == null) {
                return cb.conjunction(); // 조건 없음
            }
            return cb.equal(root.get("lastName"), lastName);
        };
    }
    
    public static Specification<Customer> getfirstName(String firstName) {
        return (root, query, cb) -> {
            if (firstName == null) {
                return cb.conjunction(); // 조건 없음
            }
            return cb.equal(root.get("firstName"), firstName);
        };
    }
    
    
	public static Specification<Customer> getStatus(Long status) {
        return (root, query, cb) -> {
            if (status == null) {
                return cb.conjunction(); // 조건 없음
            }
            return cb.equal(root.get("status"), 1);
        };
    }
}
