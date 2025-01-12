package com.example.demo;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataSourceTest {

	@Autowired DataSource dataSource;
	
	@Test
	public void test() {
		try ( Connection conn = dataSource.getConnection()) {
			System.out.println(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
