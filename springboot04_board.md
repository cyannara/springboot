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

logging.level.root=info
logging.level.org.springframework.web=debug
logging.level.com.example.demo=debug

logging.pattern.console=[%d{HH:mm:ss}] %-5level %logger{36} - [%L]%msg%n

##actuator
management.endpoints.web.exposure.include="*"
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

### 9. Service 테스트

```java

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

view page

- register.html
- modify.html
- get.html
- list.html

### 11. Controller 테스트

```java

```