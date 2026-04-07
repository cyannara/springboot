# maven

npm 모듈 설치 위치

```
C:\Users\admin>npm root -g
C:\Users\admin\AppData\Roaming\npm\node_modules

```

# JDBC(Java Database Connectivity)

### 목차

1. JDBC API의 구조
2. 드라이버 유형
3. JDBC 처리과정: 연결 설정부터 SQL 실행 및 결과 처리까지의 전반적인 메커니즘
4. Statement, PreparedStatement, CallableStatement의 차이점
5. 데이터 보존을 위한 트랜잭션 관리 및 예외 처리 방법
6. HikariCP와 같은 커넥션 풀링 기술

## JDBC 처리과정

```
1. 드라이버 로드              (은행 (하나, 국민..) 선택)
      ▼
2. 연결 객체(Connection) 생성 (은행 창구 연결/로그인)
      ▼
3. 문장 객체(Statement) 생성  (업무요청서(잔액조회, 계좌이체) 작성 - 계좌번호, 날짜 등)
      ▼
4. SQL 문 실행                (직원에게 요청 제출)
      ▼
5. 결과 받기(ResultSet)       (잔액/결과 확인)
      ▼
6. 자원 반납(Close)           (창구 종료/로그아웃)
```

### 예제

```java
package day10JDBC.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Jdbc01Test {
	   public static void main(String[] args) {

//	        String url = "jdbc:h2:mem:testdb"; // 인메모리 DB
//	        String username = "sa";
//	        String password = "";

			String jdbc_driver = "oracle.jdbc.OracleDriver";
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
			String username = "hr";
			String password = "hr";

	        try (
	            Connection conn = DriverManager.getConnection(url, username, password);
	            Statement stmt = conn.createStatement()
	        ) {

	            // 1. 테이블 생성
	            stmt.execute("CREATE TABLE users (id INT PRIMARY KEY, name VARCHAR(50))");

	            // 2. 데이터 삽입
	            stmt.executeUpdate("INSERT INTO users (id, name) VALUES (1, 'Alice')");
	            stmt.executeUpdate("INSERT INTO users (id, name) VALUES (2, 'Bob')");

	            // 3. 데이터 조회
	            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

	            // 4. 출력
	            while (rs.next()) {
	                System.out.println(
	                    rs.getInt("id") + " / " + rs.getString("name")
	                );
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}

```

### 재활용. 유지보수

기능별로 메서드 분리
반복되는 코드는 메서드로

1. 하드 코딩, Statment
   문제: 값이 코드에 박혀 있어 재사용 불가

```java
	public void insertUsers(Connection conn) throws SQLException {

		String sql = "INSERT INTO users (id, name) VALUES (1, 'Alice')";

		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sql);
			System.out.println("데이터 삽입 완료");
		}

	}
```

2. 파라미터, Statment
   재사용할 수 있게 작성
   문제: statement 는 SQL Injection 취약함

   > `SQL Injection` : 사용자가 SQL을 조작하는 공격

```java
	public void insertUser(Connection conn, int id, String name) throws SQLException {
		String sql = "INSERT INTO users (id, name) VALUES (" + id + ", '" + name + "')";

		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sql);
			System.out.println("데이터 삽입 완료: ");
		}
	}
```

`SQL Injection 공격 예시`

```java
    # sql 구문
    String sql = "SELECT * FROM users WHERE id = '" + id + "' AND password = '" + password + "'";

    # 입력값
    id: admin
    password: ' OR '1'='1

    # 완성된 쿼리 : 비밀번호 몰라도 로그인 성공
    String sql = "SELECT * FROM users WHERE id = 'admin' AND password = '' OR '1'='1'";

    # 입력값  -> 데이터 삭제
    id: 1
    password: ' OR '1'='1'; DELETE FROM users; --

```

3. 파라미터, PreparedStatment
   Statement에 비해 🔐 보안 + ⚡ 성능이 강화됨  
   문제: 파라미터 증가 시 유지보수 시에 문제 발생

   👉 `Statement`는 문장을 합치는 방식. SQL 구조 깨짐. 매번 SQL 파싱  
   👉 `PreparedStatement`는 값을 꽂는 방식. 그냥 문자열로 처리. 한 번만 파싱(속도 ↑). 가독성 ↑

```java
	public void insertUserPrepared(Connection conn, int id, String name) throws SQLException {
		String sql = "INSERT INTO users (id, name) VALUES (?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.executeUpdate();

			System.out.println("데이터 삽입 완료");
		}
	}
```

실습문제: 주소(addr) 컬럼 추가

4. ✅⭐ `VO 사용`  
   파라미터를 VO 로 묶음(📦 객체화)  
   -> 컬럼 추가돼도 메서드 변경 최소화  
   -> 확장성 ↑, 유지보수 수월  
   VO는 데이터를 전달하는 박스다

```java
insertUser(conn, 1, "Alice", "email", "phone", ...)

```

```java
	public void insertUserVO(Connection conn, User user) throws SQLException {
		String sql = "INSERT INTO users (id, name) VALUES (?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, user.getId());
			pstmt.setString(2, user.getName());
			pstmt.executeUpdate();

			System.out.println("데이터 삽입 완료");
		}
	}
```

5. 다건 처리  
   List + 반복문  
   문제: DB에 여러 번 요청 → 성능 저하

```java
	public void insertUsers(Connection conn, List<User> users) throws SQLException {
		String sql = "INSERT INTO users (id, name) VALUES (?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			for (User user : users) {
				pstmt.setInt(1, user.getId());
				pstmt.setString(2, user.getName());
				pstmt.executeUpdate();
			}

			System.out.println("여러 건 데이터 삽입 완료");
		}
	}
```

5. batch 처리 (addBatch(), executeBatch())
   DB 호출 횟수 감소하고 대량 데이터 처리 최적화 -> 🚀 성능 최적화

```java
	public void insertUsersBatch(Connection conn, List<User> users) throws SQLException {
		String sql = "INSERT INTO users (id, name) VALUES (?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			for (User user : users) {
				pstmt.setInt(1, user.getId());
				pstmt.setString(2, user.getName());

				pstmt.addBatch(); // 쿼리 모으기
			}

			int[] results = pstmt.executeBatch(); // 한 번에 실행

			System.out.println("Batch 실행 완료");

			// 결과 확인
			for (int count : results) {
				System.out.println("처리된 행 수: " + count);
			}
		}
	}
```

실습 : hobby  
1:N 관계: 하나의 회원이 여러 취미를 가지면 테이블을 분리한다

```sql
CREATE TABLE user_hobbies (
    hobby_id NUMBER PRIMARY KEY,
    user_id NUMBER NOT NULL,
    hobby_name VARCHAR2(50) NOT NULL,

    CONSTRAINT fk_user
    FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);
```

### connection 주입방법

1. static 메서드
2. 상속
3. 주입

### connectionpool

결과 출력 -> 결과를 VO에 답기 -> stream 사용

### transaction

### 싱글톤
