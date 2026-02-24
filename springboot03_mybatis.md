# Mybatis
referer  
- [Mybatis-Spring](https://mybatis.org/spring/factorybean.html)

### 라이브러리 의존성 추가
- mybatis-spring-boot-starter
- mybatis-spring-boot-starter-test : @MybatisTest 사용하려면 필요

```xml
		<dependency>
			<groupId>com.oracle.database.jdbc</groupId>
			<artifactId>ojdbc11</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>3.0.5</version>
		</dependency>
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter-test</artifactId>
			<version>3.0.5</version>
			<scope>test</scope>
		</dependency>
```

### Mbatis 설정
```java
package com.yedam.app.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("com.yedam.app")
public class MyBatisConfig {

	// DataSource Bean 등록
	@Bean
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setJdbcUrl("jdbc:oracle:thin:@localhost:1521/xe");
		dataSource.setUsername("hr");
		dataSource.setPassword("hr");
		return dataSource;
	}

	// SqlSessionFactory Bean 등록
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath*:/mappers/**/*.xml"));

		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setMapUnderscoreToCamelCase(true);
    //default: org.apache.ibatis.logging.slf4j.Slf4jImpl
    //configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
		factoryBean.setConfiguration(configuration);
		return factoryBean.getObject();
	}
}
```

### DAO 클래스
EmployeesDto
```java
package com.yedam.app.dao;

import java.util.Date;
import org.apache.ibatis.type.Alias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
//@Alias("EmployeesDto")
public class EmployeesDto {
	private String employeeId;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private Date hireDate;
	private String jobId;
	private String salary;
	private String commissionPct;
	private String managerId;
	private String departmentId;
}
```
mapper xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="Employees">
	
	<!-- 단건조회 -->
	<select id="findById" 
	        resultType="com.yedam.app.dao.EmployeesDto" 
	        parameterType="long">
		SELECT EMPLOYEE_ID,
			   FIRST_NAME,
		       LAST_NAME 
		  FROM employees 
		 WHERE employee_id = #{employeeId} 		
	</select>
	
	
</mapper>
```

EmployeeDAO
```java
package com.yedam.app.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class EmployeeDAO {

	private final SqlSessionTemplate sqlSession;
	
	public List<EmployeesDto> findAll() {
		return sqlSession.selectList("Employees.findAll");
	}

	public EmployeesDto findById(Long id) {
		return sqlSession.selectOne("Employees.findById", id);
	}
	
	public List<EmployeesDto> findBydeptAndName(Long departmentId, 
			                             String firstName){
		Map<String, Object> map = new HashMap<>();
		map.put("departmentId", departmentId);
		map.put("firstName", firstName);
		return sqlSession.selectList("Employees.findBydeptAndName", map);
	}
	
	public int insert(EmployeesDto dto) {
		return sqlSession.insert("Employees.insert", dto);
	}
	
	public int update(EmployeesDto dto) {
		return sqlSession.update("Employees.update", dto);
	}
	
	public int delete(Integer employeeId) {
		return sqlSession.delete("Employees.delete", employeeId);
	}
}

```
## junit test
통합 테스트 → @SpringBootTest


```java
package com.yedam.app;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yedam.app.config.MyBatisConfig;
import com.yedam.app.dao.EmployeeDAO;
import com.yedam.app.dao.EmployeesDto;


@SpringBootTest(classes = {MyBatisConfig.class})
public class EmplpyeeDaoTest {

	@Autowired
	EmployeeDAO dao;
	
	@Test
	public void test() {
		//given
		Long employeeId = 100l;
		
		//when
		EmployeesDto dto =  dao.findById(employeeId);
		System.out.println(dto);
		
		//then
		assertEquals(dto.getEmployeeId(), employeeId.toString());
	}
}

```

## log

|로그 내용 |레벨   | 설명   |
|:--       |:--    |:--     |
|Preparing |	DEBUG|sql구문 |
|Parameters|	DEBUG|파라미터|
|Total     |	DEBUG|처리건수|
|Columns   |	TRACE|조회컬럼|
|Row       |	TRACE|조회결과|

### 로그 지정

```yml
#mapper namespace 로그레벨지정
logging.level.Employees=debug
```

debug 레벨에서는 Preparing, Parameters, Total 이 보임
```
==>  Preparing: SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME FROM employees WHERE employee_id = ?
==> Parameters: 100(Long)
<==      Total: 1

```

```yml
#mapper namespace 로그레벨지정
logging.level.Employees=trace
```
debug 레벨에서는 Preparing, Parameters, Total, columns, Row 이 보임
```
 ==>  Preparing: SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME FROM employees WHERE employee_id = ?
 ==> Parameters: 100(Long)
 <==    Columns: EMPLOYEE_ID, FIRST_NAME, LAST_NAME
 <==        Row: 100, Steven, King
 <==      Total: 1
```

## spring Boot Auto Configuration
properties 설정만 하면 자동으로 bean 등록함.   

application.properties
```yml
spring.application.name=ex02mybatis

server.port=81

#Oracle DataSource
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@localhost:1521/xe
spring.datasource.username=hr
spring.datasource.password=hr

# mybatis 설정
mybatis.configuration.map-underscore-to-camel-case=true
mybatis.type-aliases-package=com.yedam.app
mybatis.mapper-locations=classpath:mappers/*.xml


# log 설정
logging.level.Employees=debug
logging.pattern.console= %-5level %logger - [%L]%msg%n
```

### Mapper Interface
config 설정파일에 @MapperScan(basePackages = "com.yedam.app")을 추가하거나 mapper interface에 @Mapper를 추가
```java
@MapperScan(basePackages = "com.yedam.app")
@SpringBootApplication
public class ExMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExMybatisApplication.class, args);
	}

}
```
```
@Mapper
public interface EmployeesMapper {

```

mybatis.mapper-locations설정은 mapper 인터페이스와 mapper xml이 같은 패키지에 있으면 생략 가능함. namespace를 인터페이스 패키지경로.인터페이스 이름과 동일해야함.
```
  mapper
    ├─ EmployeeMapper.xml
    └─ EmployeeMapper.java 
```

```yml
#mybatis.mapper-locations=classpath:mappers/*.xml
```

EmployeeMapper.xml
```
<mapper namespace="com.yedam.app.mapper.EmployeesMapper">
```