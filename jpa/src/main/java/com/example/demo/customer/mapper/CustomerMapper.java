package com.example.demo.customer.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.example.demo.customer.service.CustomerVO;

@Mapper
public interface CustomerMapper {

	public List<CustomerVO> getCustomer(CustomerVO CustomerVO);

}