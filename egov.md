
## 공통컴포넌트 추가  

### DB 계정 생성
```
create user com identified by com01;
grant resource, connect, create view to com;
```

### 컴포넌트 추가
파일위치 : src\main\resources\egovframework\egovProps\globals.properties  
데이터베이스 기본계정은 com/com01   

Globals.MainPage  =/EgovContent.do  

### 브라우저 테스트
로그인 계정 : USER/rhdxhd12  

### 새로운 기능 추가
1. 테이블 생성  
    ```sql
    create table todo (
      no number primary key,
      title varchar2(100),
      complete char(1) default 'n'
    )
    ```

2. 패키지 생성
    <pre>
    egovframe.tdo.service      : service, vo,
                .service.impl : dao, serviceImpl
                .web          : controller
    </pre>

3. CRUD Program  
mapper(xml) : /src/main/resources/egovframework/mapper  
view : /egovweb/src/main/webapp/WEB-INF/jsp

## MyBatis 적용

### reference  
- 실행환경 > MyBatis > [표준프레임워크 기반 적용 가이드](https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte2:psl:dataaccess:mybatisguide)  
- 기술지원 > [적용지원서비스](https://www.egovframe.go.kr/home/sub.do?menuNo=67) > 이용절차 3단계 > 가이드 다운로드

### MyBatis 사용을 위한 XML 설정파일 3가지

1. MyBatis 공통설정파일 ( `<configuration> ~ </configuration>` )  
2. SQL 매핑파일 ( `<mapper> ~ </ mapper >` )  
3. SqlSessionFactoryBean 빈설정     

    ```xml
	<!-- Mybatis setup for Mybatis Database Layer -->
	<bean id="egov.sqlSession" class="org.mybatis.spring.SqlSessionFactoryBean">		
		<property name="dataSource" ref="egov.dataSource"/>
		<property name="configLocation" value="classpath:/egovframework/mapper/config/mapper-config.xml" />
		
		<property name="mapperLocations">
			<list>
				<value>classpath:/egovframework/mapper/com/**/*_${Globals.DbType}.xml</value>
				<value>classpath:/egovframework/mapper/tdo/**/*.xml</value>
			</list>
		</property>
	</bean>
	
	<alias name="egov.sqlSession" alias="sqlSession"/>
    ```


### MyBatis 적용
1. EgovAbstractMapper 클래스 상속/확장  
    ```java
    @Repository("employeeMapper")
    public class EmployeeMapper extends EgovAbstractMapper { … }
    ```

2. Mapper Interface 방식
    ```java
    import egovframework.rte.psl.dataaccess.mapper.Mapper;  // import 변경

    @Mapper("employeeMapper")
    public interface EmployeeMapper { … }
    ```
### MyBatis Mapper Interface 사용을 위한 설정

1. pom.xml 변경  

    ```xml
    <dependency>
      <groupId>egovframework.rte</groupId>
      <artifactId>egovframework.rte.psl.dataaccess</artifactId>
      <version>2.7.0</version>
    </dependency>
    ```

    기존의 dataaccess 주석처리하고 dependency 추가. groupId가 변경되었음  

2. MapperConfigurer 빈설정
    ```xml
    <!-- MapperConfigurer setup for MyBatis Database Layer -->
    <bean class="egovframework.rte.psl.dataaccess.mapper.MapperConfigurer">
    <property name="basePackage" value=" 스캔할 Mapper Interface가 속한 풀패키지명" />
    </bean>
    ```