## 댓글 구현

### 작업순서

1. `테이블 생성`과 더미 데이터 생성
2. `DTO` 선언
3. `Mapper` 인터페이스, `Mapper XML`
4. `Service` 인터페이스, `ServiceImpl` 구현 클래스
5. `Controller` 클래스
6. 뷰페이지 Javascript `ajax`

## 1. 테이블 생성

```sql
create table tbl_reply (
  rno number(10,0),
  bno number(10,0) not null,
  reply varchar2(1000) not null,
  replyer varchar2(50) not null,
  replyDate date default sysdate,
  updateDate date default sysdate
);

create sequence seq_reply;

alter table tbl_reply add constraint pk_reply primary key (rno);

alter table tbl_reply  add constraint fk_reply_board
foreign key (bno)  references  tbl_board (bno);

insert into tbl_reply (rno, bno, reply, replyer)  values ( seq_reply.nextval, 1, '댓글1', '홍길동' );
```

<img src="./images/reply01.png" width="600px">

## 패키지 구성

<img src="./images/reply02.png" width="1100px">

<pre>
com.example.demo.board.controller     - ReplyController
                      .mapper         - ReplyMapper 
                      .service        - ReplyService, ReplyDTO, ReplyPageDTO
                      .service.impl   - ReplyServiceImpl  
src/main/resources/mappers            - ReplyMapper.xml                                      
</pre>

ReplyDTO

```java
rno
bno
reply
replyer
replydate
updatedate
```

ReplyPageDTO

```java
@Data
@AllArgsConstructor
public class ReplySearchDTO {

	int page;
	int amount;

	public int getStart() {
		return (page-1)*amount +1;
	}

	public int getEnd() {
		return page*amount;
	}
}
```

ReplyMapper xml

```xml

```

ReplyMapper

```java

```

ReplyService

```java

```

ReplyServiceImpl

```java

```

ReplyServiceTest


```java
@Slf4j
@SpringBootTest
public class ReplyServiceTest {

	@Autowired
	ReplyService replyService;

	@Test
	@DisplayName("댓글 수정")
	public void update() {
		// given
		ReplyDTO reply = ReplyDTO.builder()
				   .rno(2L)          // 실행전 존재하는 번호인지 확인할 것
				   .reply("댓글수정")
				   .replyer("김수정")
				   .build();

		// when
		boolean result = replyService.modify(reply);

		// then
		assertThat(result).isEqualTo(true);
	}

}

```

ReplyController 설계

| 작업   | URL                      | HTTP 전송방식 |
| :----- | :----------------------- | :------------ |
| 등록   | /replys/new              | POST          |
| 조회   | /replys/:rno             | GET           |
| 삭제   | /replys/:rno             | DELETE        |
| 수정   | /replys/:rno             | PUT or PATCH  |
| 페이지 | /replys/pages/:bno/:page | GET           |
|        |                          |               |

ReplyControllerTest

```java

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
public class ReplyControllerTest {

    @Autowired MockMvc mvc;

    @Autowired ObjectMapper objectMapper;

    //@Test
    @DisplayName("reply 등록")
    void register() throws Exception {
    	//given
    	String requestBody = """
    			{
    			  "bno": 1,
    			  "reply": "댓글등록",
    			  "replyer": "이순신"
    			}
    			""";

    	//when //then
    	mvc.perform(post("/replies/new")
    	    	     .content(requestBody)
    	    	     .contentType(MediaType.APPLICATION_JSON_VALUE)
    	)
    	.andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print());

    }

        //@Test
    @DisplayName("reply 단건 조회")
    void getreply() throws Exception {

    	//given
    	Long rno = 2L;
    	String url = "/replies/"+ rno;

    	//when
    	mvc.perform(
    			get(url).
    			accept(MediaType.APPLICATION_JSON_VALUE)
    	)
    	//then
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$.reply").value("댓글수정"))
       .andDo(print())  ;

    }

    @Test
    @DisplayName("게시글의 reply 조회")
    void getreplyByBno() throws Exception {

    	//given
    	Long bno = 1L;
    	int page = 1;
    	//String url = String.format("/replies/pages/%d/%d", bno, page);

    	//when
    	String responseBody = mvc.perform(
    			get("/replies/pages/{bno}/{page}",bno, page)
    			.accept(MediaType.APPLICATION_JSON_VALUE)
    	)
    	.andReturn().getResponse().getContentAsString();

    	//then
    	String json = objectMapper.readTree(responseBody).toPrettyString();
    	log.debug(json);

    	ReplyPageDTO dto = objectMapper.readValue(responseBody, ReplyPageDTO.class);

    	log.debug("replyCnt : " + dto.getReplyCnt() );
    	log.debug("first reply : " + dto.getList().get(0).getReply() );
    }
```

MocMvc 요청 메서드

| 메서드      | 용도                                            | 예시                                    |
| :---------- | :---------------------------------------------- | :-------------------------------------- |
| get         | GET 메서드. pathvariable은 두번째 인수부터 지정 | get("/replyies/{rno}","1")              |
| post        | POST 메서드                                     | post("/replyies/new")                   |
| put         | PUT 메서드                                      | delete("/replyies/{rno}","1")           |
| delete      | DELETE 메서드                                   | put("/replyies/{rno}","1")              |
| contentType | 요청 헤더의 Content-Type을 지정                 | ContentType(MediaType.APPLICATION_JSON) |
| header      | 임의의 요청 헤더를 지정                         | header("CUSTOMER_HEADER","foo")         |
| content     | 요청 바디를 지정                                | content("{\"reply\":\"홍길동\"}")       |

MocMvc 응답 메서드

| 메서드   | 용도                        | 예시                                                         |
| :------- | :-------------------------- | :----------------------------------------------------------- |
| status   | 상태코드 확인               | status().isNoContent()                                       |
| header   | 응답헤더의 값을 확인        | header().string("Location", "http://localhost:81/replies/1") |
| content  | 응답바디의 내용을 확인      | content().json("{}")                                         |
| jspnPath | 응답바디의 json 내용를 확인 | jsonPath("$.reply").value("댓글")                            |
| xpath    | 응답바디의 xml 내용을 확인  | xpath("/replies/reply").string("댓글")                       |
|          |                             |                                                              |
