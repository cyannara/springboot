## 게시판 구현

### 작업순서

1. `spring starter project` 생성
2. `application.properties` 설정
3. `log4jdbc` 설정
4. `테이블 생성`과 더미 데이터 생성
5. DTO 선언
6. 영속계층 : `Mapper` 인터페이스 와 `Mapper XML`
7. Mapper 테스트
8. 비즈니스 계층 : `Service` 인터페이스와 `ServiceImpl` 구현 클래스
9. Service 테스트
10. 프리젠테이션 계층 : `Controller` 와 `뷰페이지`
11. Controller 테스트

### 1. spring starter project 생성

type : `Maven`, Gradle  
Packageing : `jar`, war  
Java version : 21, `17`  
Language : `java` , kotlin  
dependency : `Spring web`, `thymeleaf`, `Oracle`, `Mybatis`, `Lombok`, `devTools`, `Actuator`

### 2. application.properties

```properties
spring.application.name=demo

##server
server.port=81
#server.servlet.context-path=web

##Oracle DataSource
#spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
#spring.datasource.url=jdbc:oracle:thin:@localhost:1521/xe
spring.datasource.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy
spring.datasource.url=jdbc:log4jdbc:oracle:thin:@localhost:1521/xe
spring.datasource.username=hr
spring.datasource.password=hr
spring.datasource.hikari.maximum-pool-size=3

##MyBatis
mybatis.configuration.map-underscore-to-camel-case=true
mybatis.configuration.jdbc-type-for-null=VARCHAR
mybatis.type-aliases-package=com.example.demo
mybatis.mapper-locations=classpath:mappers/*.xml

##log
logging.level.jdbc.resultsettable=debug
logging.level.jdbc.sqlonly=debug
logging.level.jdbc.resultset=off
logging.level.jdbc.connection=off
logging.level.jdbc.audit=off

logging.level.root=info
logging.level.org.springframework.web=debug
logging.level.com.example.demo=debug

logging.pattern.console=[%d{HH:mm:ss}] %-5level %logger{36} - [%L]%msg%n

##actuator
management.endpoints.web.exposure.include=*
```

### 3. log4jdbc 설정

- dependency 추가 (pom.xml)

```xml
		<dependency>
			<groupId>org.bgee.log4jdbc-log4j2</groupId>
			<artifactId>log4jdbc-log4j2-jdbc4.1</artifactId>
			<version>1.16</version>
		</dependency>
```

- log4jdbc.log4j2.properties 파일 생성

```properties
log4jdbc.spylogdelegator.name=net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator
log4jdbc.dump.sql.maxlinelength=0
```

- application.properties datasource 수정

```properties
spring.datasource.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy
spring.datasource.url=jdbc:log4jdbc:oracle:thin:@localhost:1521/xe
```

- application.properties log 레벨 지정

```properties
logging.level.root=info
logging.level.jdbc.resultsettable=debug
logging.level.jdbc.sqlonly=debug
logging.level.org.springframework.web=debug
logging.level.com.example.demo=debug
```

### 4. 테이블 생성과 더미 데이터 생성

```
create sequence seq_board;

create table tbl_board (
  bno number(10,0),
  title varchar2(200) not null,
  content varchar2(2000) not null,
  writer varchar2(50) not null,
  regdate date default sysdate,
  updatedate date default sysdate
);

alter table tbl_board add constraint pk_board primary key (bno);

insert into tbl_board (bno, title, content, writer)  values ( seq_board.nextval, '제목', '내용', '작성자' );
```

### 5. DTO 선언

```
  bno
  title
  content
  writer
  regdate
```

### 6. 영속계층 : Mapper XML 과 Mapper 인터페이스

- Mapper 인터페이스 구성  
  insert: 등록  
  update: 수정  
  delete: 삭제  
  read: 단건조회  
  getList : 전체조회

- BoardMapper.xml

```xml

<insert id="insert">
  INSERT INTO tbl_board (
      bno,
      title,
      content,
      writer
  )
  VALUES (
      seq_board.nextval,
      #{title},
      #{content},
      #{writer}
  )
</insert>

<insert id="insertSelectKey">

  <selectKey keyProperty="bno" order="BEFORE" resultType="long">
    SELECT seq_board.nextval
      FROM dual
  </selectKey>
    INSERT INTO tbl_board (
      bno,
      title,
      content,
      writer)
    VALUES (
      #{bno},
      #{title},
      #{content},
      #{writer})
</insert>

<update id="update">
  UPDATE tbl_board
     SET
         title = #{title},
         content = #{content},
         writer = #{writer},
         updateDate = sysdate
   WHERE bno = #{bno}
</update>

<delete id="delete">
  DELETE tbl_board
   WHERE bno = #{bno}
</delete>

<select id="read" resultType="BoardVO">
  SELECT bno,
         title,
         content,
         writer,
         regdate
    FROM tbl_board
   WHERE bno = #{bno}
</select>

<select id="getList" resultType="BoardVO">
  SELECT bno,
         title,
         content,
         writer,
         regdate
    FROM tbl_board
  <![CDATA[
   WHERE bno > 0
  ]]>
</select>
```

-BoardMapper 인터페이스

```java
pulic interface BoardMapper {

}
```

### 7. Mapper Test

테스트 코드 작성 패턴 : given-when-then

- given : 테스트 실행을 준비하는 단계
- when : 테스트를 진행하는 단계
- then : 테스트 결과를 검증하는 단계

```java
@Log4j
@SpringBootTest
public class BoardMapperTests {

  @Setter(onMethod_ = @Autowired)
  private BoardMapper mapper;

  @Test
  @DisplayName("게시글전체조회")
  public void testGetList() {
    //given

    //when
    List<BoardDTO> list = mapper.getList()

    //then
    list.forEach(board -> log.info(board));
    assertNotNull(list);
  }

  @Test
  @DisplayName("게시글 등록")
  public void testInsert() {
    //given
    BoardVO board = new BoardVO();
    board.setTitle("새로 작성하는 글");
    board.setContent("새로 작성하는 내용");
    board.setWriter("newbie");

    //when
    int cnt = mapper.insert(board);

    //then
    log.info(board);
    assertEquals(cnt, 1);
	}

  @Test
  @DisplayName("selectkey가 있는 게시글 등록")
  public void testInsertSelectKey() {
    //given
    BoardVO board = new BoardVO();
    board.setTitle("새로 작성하는 글 select key");
    board.setContent("새로 작성하는 내용 select key");
    board.setWriter("newbie");
    //when
    int cnt = mapper.insertSelectKey(board);

    //then
    log.info(board);
    assertEquals(cnt, 1);
  }

  @Test
  @DisplayName("게시글 상세조회")
  public void testRead() {
    //given
    long bno = 5L;

    //when
    BoardVO board = mapper.read(bno);

    //then
    log.info(board);
    assertEquals(board.getBno(), bno);
  }

  @Test
  @DisplayName("게시글 삭제")
  public void testDelete() {
    //given
    long bno = 5L;

    //when
    int cnt = mapper.delete(3L)

    //then
    log.info("DELETE COUNT: " + cnt );
    assertEquals(cnt, 1);
  }

  @Test
  @DisplayName("게시글 수정")
  public void testUpdate() {
    //given
    // 실행전 존재하는 번호인지 확인할 것
    BoardVO board = BoardVO.builder()
          .bno(5L)
          .title("수정된 제목")
          .content("수정된 내용")
          .writer("user00")
          .build();

    //when
    int cnt = mapper.update(board);

    //then
    log.info("UPDATE COUNT: " + cnt);
    assertEquals(cnt, 1);
  }
```

### 8. 비즈니스 계층 : `Service` 인터페이스와 `ServiceImpl` 구현 클래스

Service 인터페이스 구성

- register: 등록 -> mapper insert
- modify: 수정 -> mapper update
- remove: 삭제 -> mapper delete
- get: 단건조회 -> mapper read
- getList : 전체조회 -> mapper getList

```java
public interface BoardService {

	public void register(BoardVO board);

	public boolean modify(BoardVO board);

	public boolean remove(Long bno);

	public BoardVO get(Long bno);

	public List<BoardVO> getList();

}
```

BoardServiceImpl

```java

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.board.mapper.BoardMapper;
import com.example.demo.board.service.BoardDTO;
import com.example.demo.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardMapper boardMapper;

	@Override
	public void register(BoardDTO board) {
		boardMapper.insert(board);
	}
	@Override
	public boolean modify(BoardDTO board) {
		return boardMapper.update(board) == 1 ? true : false;
	}
	@Override
	public boolean remove(Long bno) {
		return boardMapper.delete(bno) == 1 ? true : false;
	}
	@Override
	public BoardDTO get(Long bno) {
		return boardMapper.read(bno);
	}
	@Override
	public List<BoardDTO> getList() {
		return boardMapper.getList();
	}
}
```

### 9. Service 테스트

```java

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.board.service.BoardDTO;
import com.example.demo.board.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class BoardServiceTest {

	@Autowired
	BoardService boardService;

	@Test
	@DisplayName("게시글 수정")
	public void update() {
		// given
		// 실행전 존재하는 번호인지 확인할 것
		BoardDTO board = BoardDTO.builder()
				   .bno(4L)
				   .title("서비스수정")
				   .content("서비스 내용")
				   .writer("user00")
				   .build();

		// when
		boolean result = boardService.modify(board);

		// then
		assertThat(result).isEqualTo(true);
	}

}
```

### 10. 프리젠테이션 계층 : `Controller` 와 `뷰페이지`

- BoardController

```java
@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

	private BoardService service;

	@GetMapping("/register")
	public void register() {	}

	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {

		log.info("register: " + board);

		service.register(board);

		rttr.addFlashAttribute("result", board.getBno());

		return "redirect:/board/list";
	}

	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("bno") Long bno, Model model) {

		log.info("/get or modify");
		model.addAttribute("board", service.get(bno));
	}

	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("modify:" + board);

		if (service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}

		return "redirect:/board/list";
	}

	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) 	{

	  log.info("remove..." + bno);
	  if (service.remove(bno)) {
	    rttr.addFlashAttribute("result", "success");
	  }
	  return "redirect:/board/list";
	}


	@GetMapping("/list")
	public void list(Model model) {

	  log.info("list");
	  model.addAttribute("list", service.getList());

	}

}
```

### 11. Controller 테스트

@AutoConfigureMockMvc  
웹 환경에서 컨트롤러를 테스트 하려면 반드시 서블릿 컨테이너가 구동되고, DispatcherServlet 객체가 메모리에 올라가야 하지만, 서블릿 컨테이너를 모킹하면 실제 서블릿 컨테이너가 아닌 테스트용 모형 컨테이너를 사용하기 때문에 간단하게 컨트롤러를 테스트 할 수 있다. @SpringBootTest위에 지정.

@WebMvcTest  
웹에서 테스트하기 힘든 컨트롤러를 테스트할 때 적합하며, MockMvc를 의존성 주입한다. @SpringBootTest와 같이 사용될 수 없다

MockMvc

- perform()를 통해 요청 메서드에 따라 MockMvc를 실행시킬 수 있다.
- andExpect() : 응답을 검증하는 역할을 한다.
- andDo(print()) : 요청/응답 메시지를 확인할 수 있다.

```java
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class BoardControllerTest {

    @Autowired MockMvc mvc;

    //@Test
    @DisplayName("조회 컨트롤러")
    void list() throws Exception {
    	mvc.perform(get("/board/list"))
    	   .andExpect(status().isOk());
    	 //  .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("등록 컨트롤러")
    void register() throws Exception  {
    	String param = "title=moctest&content=내용&writer=kim";
    	mvc.perform(post("/board/register")
    	   .content(param)
    	   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
    	)
    	 //.andExpect(status().isOk())
         //.andExpect((ResultMatcher) content().string("main"))
         //.andDo(MockMvcResultHandlers.print());
         ;
    }
}
```

### 12. view 페이지

view page

- register.html
- modify.html
- get.html
- list.html

register.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
  <head>
    <meta charset="UTF-8" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
    <title>Insert title here</title>
  </head>
  <body>
    <div class="container">
      <h3>게시글</h3>
      <div class="panel-heading">
        <button type="button" id="btnRegister" class="btn btn-info">
          게시글 등록
        </button>
      </div>
      <table class="table">
        <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성일자</th>
          </tr>
        </thead>
        <tbody>
          <tr th:each="board : ${list}">
            <td th:text="${board.bno}">1</td>
            <td
              th:text="${board.title}"
              th:onclick="|location.href='get?bno=${board.bno}'|"
            >
              제목
            </td>
            <td th:text="${board.writer}">홍길동</td>
            <td th:text="${board.regdate}">2025/01/06</td>
          </tr>
        </tbody>
      </table>
    </div>

    <script th:inline="javascript">
      const result = [[${result}]]
      if( result ) {
      	alert("등록완료");
      }

      btnRegister.addEventListener("click", ()=>{
      	location.href="/board/register";
      })
    </script>
  </body>
</html>
```

modify.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
  <head>
    <meta charset="UTF-8" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
    <title>modify.html</title>
  </head>
  <body>
    <div class="container">
      <h3>게시글 수정</h3>
      <form action="modify" method="post" th:object="${board}">
        <div class="mb-3">
          <label for="bno" class="form-label">번호</label>
          <input
            type="text"
            class="form-control"
            readonly="readonly"
            th:field="*{bno}"
          />
        </div>
        <div class="mb-3">
          <label for="title" class="form-label">제목</label>
          <input type="text" class="form-control" th:field="*{title}" />
        </div>
        <div class="mb-3">
          <label for="content" class="form-label">내용</label>
          <textarea
            class="form-control"
            rows="3"
            th:field="*{content}"
          ></textarea>
        </div>
        <div class="mb-3">
          <label for="writer" class="form-label">작성자</label>
          <input type="text" class="form-control" th:field="*{writer}" />
        </div>
        <button class="btn btn-success">등록</button>
      </form>
    </div>
  </body>
</html>
```

get.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
  <head>
    <meta charset="UTF-8" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
    <title>Insert title here</title>
  </head>
  <body>
    <div class="container">
      <table class="table">
        <tr>
          <th>번호</th>
          <td th:text="${board.bno}"></td>
        </tr>
        <tr>
          <th>제목</th>
          <td th:text="${board.title}"></td>
        </tr>
        <tr>
          <th>내용</th>
          <td th:text="${board.content}"></td>
        </tr>
        <tr>
          <th>작성자</th>
          <td th:text="${board.writer}"></td>
        </tr>
        <tr>
          <th>작성일자</th>
          <td th:text="${board.regdate}"></td>
        </tr>
      </table>
      <button th:onclick="|location.href='modify?bno=${board.bno}'|">
        수정
      </button>
      <button th:onclick="|location.href='remove?bno=${board.bno}'|">
        삭제
      </button>
    </div>
  </body>
</html>
```

list.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
  <head>
    <meta charset="UTF-8" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
    <title>Insert title here</title>
  </head>
  <body>
    <div class="container">
      <h3>게시글</h3>
      <div class="panel-heading">
        <button type="button" id="btnRegister" class="btn btn-info">
          게시글 등록
        </button>
      </div>
      <table class="table">
        <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성일자</th>
          </tr>
        </thead>
        <tbody>
          <tr th:each="board : ${list}">
            <td th:text="${board.bno}">1</td>
            <td
              th:text="${board.title}"
              th:onclick="|location.href='get?bno=${board.bno}'|"
            >
              제목
            </td>
            <td th:text="${board.writer}">홍길동</td>
            <td th:text="${board.regdate}">2025/01/06</td>
          </tr>
        </tbody>
      </table>
    </div>

    <script th:inline="javascript">
      const result = [[${result}]]
      if( result ) {
      	alert("등록완료");
      }

      btnRegister.addEventListener("click", ()=>{
      	location.href="/board/register";
      })
    </script>
  </body>
</html>
```
