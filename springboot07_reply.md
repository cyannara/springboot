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
```

<img src="./images/reply01.png" width="600px">

## 패키지 구성

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
@Getter
public class ReplyPageDTO {

  private int replyCnt;
  private List<ReplyVO> list;
}
```
