# EgovFrame

### eGovFrame Template Porject

1. Common All-in-one  
   <img src="./images/egov/all_01.png" width="400">

2. project name, Group id 입력  
   <img src="./images/egov/all_02.png"  width="400">

3. 데이터베이스 사용자 계정 생성

   ```sql
   ALTER SESSION SET "_ORACLE_SCRIPT"=true;
   create user com identified by com01;
   grant resource, connect to com;
   ALTER USER com DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS;
   ```

   <img src="./images/egov/all_04.png"  width="500">

4. DDL, DML 스크립트 실행  
   <img src="./images/egov/all_03.png"  width="300">
5. 패키지 표현방식 -> 계층형  
   <img src="./images/egov/all_05.png" width="550">

6. properoies 파일에서 dbtyp을 오라클로 변경  
   <img src="./images/egov/all_06.png" width="550">
7. 컨트롤러, 서비스, DAO, 매퍼XML 생성  
   <img src="./images/egov/all_08.png" width="300">

8. 패키지 스캔 경로 추가  
   <img src="./images/egov/all_09.png" width="300">

9. 서비스, DAO 패키지 경로 추가(context-common.xml)  
   <img src="./images/egov/all_10.png" width="600">

10. 매퍼 XML 경로 추가(context-mapper.xml)  
    <img src="./images/egov/all_11.png"  width="600">

11. 컨트롤러 패키지 경로 추가 (egov-com-servlet.xml)
    <img src="./images/egov/all_12.png" width="700">

12. Exceptionresolver 빈 주석 처리(에러메시지 보이도록 함)
    <img src="./images/egov/all_13.png" width="700">

13. error 페이지  
    <img src="./images/egov/all_14.png" width="550">

14. 로그 레벨 제어  
    <img src="./images/egov/all_15.png" width="500">
15. 권한 제어

- 권한그룹관리 : 사용자에게 권한 부여
- 권한 계층  
  <img src="./images/egov/all_21.png" width="500">

  ```sql
  SELECT a.CHLDRN_ROLE as child, a.PARNTS_ROLE parent
    FROM COMTNROLES_HIERARCHY a LEFT JOIN COMTNROLES_HIERARCHY b on (a.CHLDRN_ROLE = b.PARNTS_ROLE);

  SELECT a.ROLE_PTTRN url, b.AUTHOR_CODE authority
  FROM COMTNROLEINFO a, COMTNAUTHORROLERELATE b
  WHERE a.ROLE_CODE = b.ROLE_CODE
  AND a.ROLE_TY = 'url' ORDER BY a.ROLE_SORT;
  ```

  <img src="./images/egov/all_22.png" width="330">  
  <img src="./images/egov/all_23.png" width="400">

- 롤관리
  고객관리 페이지 접근권한  
  <img src="./images/egov/all_17.png" width="500">

  메인화면 접근권한  
  <img src="./images/egov/all_19.png" width="500">

  롤 정규표현식  
  <img src="./images/egov/all_16.png" width="500">

- 권한관리 : 권한에 롤을 부여  
  ROLE_USER에게 모든 접근제한 미등록, 회원관리, 고객관리, 메인페이지 권한 등록  
  <img src="./images/egov/all_18.png" width="500">

  <img src="./images/egov/all_20.png" width="500">

### 공통컴포넌트 사용

1. oracle 사용자 계정 생성

```sql
ALTER SESSION SET "_ORACLE_SCRIPT"=true;
create user com identified by com01;
grant resource, connect to com;
ALTER USER com DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS;
```

2. 이클립스 dataSource 지정

- 접속정보 등록  
  <img src="./images/egov/eclipse_datasource01.png">

- 드라이버 선택  
  <img src="./images/egov/eclipse_datasource02.png">

- 드라이버(jar) 지정  
  <img src="./images/egov/eclipse_datasource03.png">

- DB 연결하여 테스트  
  <img src="./images/egov/eclipse_datasource04.png"  style="width:600px">

- 컴포넌트 추가 : file-> new -> Add EgovFrame Common Compononent  
  <img src="./images/egov/egov_common01.png">

- 테이블 생성 선택  
  <img src="./images/egov/egov_common02.png">

- 데이터소스 선택하고 테이블 생성  
  <img src="./images/egov/egov_common03.png">

### 공통컴포넌트 패키지 정의서

https://www.egovframe.go.kr/home/ntt/nttRead.do?pagerOffset=20&searchKey=&searchValue=&menuNo=75&bbsId=3&nttId=561

```
egovframework.rte.....: Runtime Environment (실행환경)
egovframework.rte.fdl : Foundation layer (공통기반)
egovframework.rte.itl : Integration layer (연계)
egovframework.rte.psl : Persistence layer (데이터처리)
egovframework.rte.ptl : Presenation layer (화면처리)
```

용어명 : [행정표준용어](www.adams.go.kr)를 기준으로 명명  
Java Coding Convention : [오라클 코딩 규칙](www.oracle.com/technetwork/java/codeconvtoc-136057.html)
