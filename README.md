SpringBoot 수업
|일|내용|
| :----|:----|
| 1일차 ||
|| - STS 설치 |
| | - Springboot 프로젝트 |
| | - Mybatis |
| | - Thymeleaf |
| | - **실습** : 사원조회 |
|2일차||
||- DI|
||- log|
||- Spring MVC|
||- Controller|
||- **실습** : 부서관리|
|3일차||
||- 컨트롤러 : validation|
||- thymeleaf|
||- Service|
||- board CRUD|
|4일차||
||- validation|
||- 에러페이지|
||- 페이징, 검색|
||- **실습** : 사원관리|
|5일차||
||- layout|
||- rest API|
||- reply|
||- AOP|
||- 트랜잭션|
||- **실습** : 부서관리|
|6일차||
||- 파일업로드|
||- RestTemplate|
||- security|
|7일차||
||- JPA|
||- batch|
||- egov|
|8일차||
||- egov|
||- 시험|
|||

### 2일차 실습

수업: springboot01_start, springboot02_di.md
과제: forward vs redirect

### 3일차 실습

수업: springboot03_controller.md, springboot04_board.md  
과제: 사원관리

### 4일차 실습

수업: springboot05_paging.md  
실습: 부서조회

<pre>
com.example.demo.insa.controller   - DeptController
                     .mapper       -  DeptMapper  
                     .service      -  DeptService, DeptDTO, DeptSearchDTO
                     .service.impl -  DeptServiceImpl
mappers  - DeptMapper.xml
              전체조회  getList
              단건조회  read
</pre>

### 5일차 실습

수업: springboot06_rest.md, springboot06_reply.md  
실습및 과제: 부서관리

## boot

- oracle
- jasperReport

### boot - chat branch

- storm

## demo

- mysql
- jpa

### demo - h2 branch

- jpa(h2)

### demo - jpa_mybatis branch

- jpa + mybatis
