SpringBoot 수업

| 일  | 내용                    |
| :-- | :---------------------- |
| 1일 |                         |
|     | - STS 설치              |
|     | - Springboot 프로젝트   |
|     | - Mybatis               |
|     | - Thymeleaf             |
|     | - **실습** : 사원조회   |
| 2일 |                         |
|     | - DI                    |
|     | - log                   |
|     | - Spring MVC            |
|     | - Controller            |
|     | - **실습** : 부서관리   |
| 3일 |                         |
|     | - 컨트롤러 : validation |
|     | - thymeleaf             |
|     | - Service               |
|     | - board CRUD            |
| 4일 |                         |
|     | - validation            |
|     | - 에러페이지            |
|     | - 페이징, 검색          |
|     | - **실습** : 사원관리   |
| 5일 |                         |
|     | - layout                |
|     | - rest API              |
|     | - reply                 |
|     | - AOP                   |
|     | - 트랜잭션              |
|     | - **실습** : 부서관리   |
| 6일 |                         |
|     | - RestTemplate          |
|     | - 파일업로드            |
| 7일 |                         |
|     | - security              |
| 8일 |                         |
|     | - JPA                   |
|     | - 시험                  |
| etc |                         |
|     | - egovframe             |
|     | - batch                 |
|     | - 레포팅 툴             |
|     | - JWT                   |
|     | - 엑츄에이터            |
|     | - yml, error page       |
|     | - ec2 jenkins           |
|     | - JPA @OneToMany        |
|     | - 쿠버네티스            |
|     |                         |

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

### 6일차 실습

### 7일차 실습

### 8일차 실습

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
