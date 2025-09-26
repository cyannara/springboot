# JPA

## 목차

1. 환경 설정
2. 엔티티 선언
3. 엔티티 매핑 실습
4. 리포지토리 만들기 (EntityManager, Spring Data JPA)
5. JPQL/쿼리 메서드

## 환경설정

### 라이브러리 의존성 설정

```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>com.oracle.database.jdbc</groupId>
			<artifactId>ojdbc11</artifactId>
			<scope>runtime</scope>
		</dependency>
```

### 프로퍼티 설정

Oracle

```properties

#oracle
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@localhost:1521/xe
spring.datasource.username=jpa
spring.datasource.password=jpa

#jpa
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format-sql=true

```

Mysql

```properties

#mysql
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=jpa
spring.datasource.password=jpa

#jpa
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
#spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format-sql=true
```

### JPA 오라클 계정 생성

관리자(system) 권한으로 접속하여 사용자 계정 생성

```
ALTER SESSION SET "_ORACLE_SCRIPT"=true;
create user jpa identified by jpa;
grant resource, connect to jpa;
ALTER USER jpa DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS;
```

## 엔티티 선언

Posts

```java
package com.example.demo.domain.Posts;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "clob", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
```

## 리포지토리 만들기

### Entity Manager 사용

```java
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}

```

### repository 테스트

```java

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional // 테스트가 끝나면 롤백됨
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Commit
    @Transactional
    @Test
    void 회원저장_조회() {
        // given
        Member member = new Member();
        member.setName("홍길동");
        member.setAge(20);

        // when
        Long id = memberRepository.save(member);
        Member found = memberRepository.find(id);

        // then
        assertEquals(found.getName(), "홍길동");
        assertEquals(found.getAge(),20);
    }
}

```

### spring Data JPA

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {

}

```

### 연관관계 매핑

```java
@Data
@Entity
public class Team {
    @Id @GeneratedValue
    private Long id;

    private String name;

    // 양방향 매핑
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

}
```

Member 에 필드 추가

```java
    @ManyToOne                // N:1 관계
    @JoinColumn(name = "team_id") // FK 컬럼명
    private Team team;
```

**조인 쿼리**를 직접 쓰지 않았는데 팀 이름이 조회된다.

```java
    @Autowired EntityManager em;

    @Commit
    @Transactional
    @Test
    void 회원_팀_저장_조회() {
	    Team team = new Team();
	    team.setName("개발팀");
	    em.persist(team);

	    Member member = new Member();
	    member.setName("홍길동");
	    member.setTeam(team);
	    em.persist(member);

	    //조회
	    Member found = em.find(Member.class, member.getId());
	    System.out.println("회원 이름: " + found.getName());
	    System.out.println("팀 이름: " + found.getTeam().getName());
    }
```

PostsRepositoryTest

```java
package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Posts.Posts;
import com.example.demo.domain.Posts.PostsRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterAll
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("jojoldu@gmail.com")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
```

PostsService

```java
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
```

PostsSaveRequestDto

```java

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

	private String title;
	private String content;
	private String author;

	@Builder
	public PostsSaveRequestDto(String title, String content, String author) {
		this.title = title;
		this.content = content;
		this.author = author;
	}

	public Posts toEntity() {
		return Posts.builder()
				.title(title)
				.content(content)
				.author(author)
				.build();

	}
}
```

PostsApiController

```java
@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }
}
```

PostsApiControllerTestRestTemplate

- RestTemplate 을 이용한 테스트

```java
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.domain.Posts.Posts;
import com.example.demo.domain.Posts.PostsRepository;
import com.example.demo.domain.Posts.PostsUpdateRequestDto;
import com.example.demo.web.dto.PostsSaveRequestDto;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTestRestTemplate {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PostsRepository postsRepository;

	@AfterAll
	public void tearDown() throws Exception {
		postsRepository.deleteAll();
	}

	// @Test
	public void Posts_등록된다() throws Exception {
		// given
		String title = "title";
		String content = "content";
		PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title(title).content(content).author("author")
				.build();

		String url = "http://localhost:" + port + "/api/v1/posts";

		// when
		ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(title);
		assertThat(all.get(0).getContent()).isEqualTo(content);

	}
}
```

### 수정하기

Posts update 메서드 추가

```java
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
```

PostsUpdateRequestDto 클래스 생성

```java
@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

```

PostsService에 update 메서드 추가

```java
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }
```

PostsApiController update 메서드 추가

```java
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }
```

### JPA Auditing

등록시간, 수정시간 자동으로 관리하는 Auditing 기능 추가

1. @EntityListeners 를 지정한 클래스 생성

```java
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
```

2. 도메인 클래스에 BaseTimeEntity를 상속

```java
public class Posts extends BaseTimeEntity{
```

3. Application 클래스에 황성화 애노테이션(@EnableJpaAuditing) 추가

```java
@EnableJpaAuditing
@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

}
```

### 페이징 조회

조회에 사용될 PostsListResponseDto 클래스 추가.  
content 필드가 없고 Posts 엔티티를 DTO에 담음.

```java
@Getter
public class PostsListResponseDto {
	private Long id;
	private String title;
	private String author;
	private LocalDateTime mdoifiedDate;

	public PostsListResponseDto(Posts entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.author = entity.getAuthor();
		//this.modifiedDate = entity.getModifiedDate();
	}

}
```

PostsRepository findAllPage() 메서드 추가

```java
public interface PostsRepository extends JpaRepository<Posts, Long> {

	@Query("SELECT p FROM Posts p ORDER BY p.id DESC")
	List<Posts> findAllDesc();

	@Query("SELECT p FROM Posts p ")
	Page<PostsListResponseDto> findAllPage(Pageable pageable);
}
```

PostsService 에 findAllPaging() 메서드 추가

```java
public class PostsService {

    public Page<PostsListResponseDto> findAllPaging(Pageable pageable) {
    	int pageNumber = pageable.getPageNumber() <=0 ? 0 : pageable.getPageNumber()-1;
    	int pageSize = pageable.getPageSize();
    	pageable = PageRequest.of(pageNumber, pageSize);
        return postsRepository.findAllPage(pageable);
    }
}
```

PostsController

```java
	@GetMapping("/api/v1/posts")
	public Page<PostsListResponseDto> index(Model model,
			@PageableDefault(page = 1, size = 3, sort = "id", direction = Direction.ASC) Pageable pagable) {
		return postsService.findAllPaging(pagable);
	}
```

IndexController

```java
	@GetMapping("/")
	public String index(Model model,
  @PageableDefault(page = 1, size = 3, sort = "id", direction = Direction.ASC ) Pageable pagable) {
		//model.addAttribute("posts", postsService.findAllDesc() );
		model.addAttribute("posts", postsService.findAllPaging(pagable) );
		return "index";
	}
```

index.html

```html
<!-- 페이징 시작 -->
<nav>
  <ul
    class="pagination"
    th:with="startPage=${T(java.lang.Math).floor(posts.number/10)}*10+1,
			                                endPage=(${posts.totalPages}>${startPage}+9) ? ${startPage}+9 :${posts.totalPages}"
  >
    <li class="page-item" th:classappend="${posts.first} ? disabled">
      <a class="page-link" th:href="|javascript:gopage(${posts.number})|"
        >Previous</a
      >
    </li>

    <li
      th:each="num : *{#numbers.sequence(startPage, endPage)}"
      class="page-item"
      th:classappend="${num} == ${posts.number}+1 ? active"
    >
      <a
        class="page-link"
        th:href="|javascript:gopage(${num})|"
        th:text="${num}"
        >2</a
      >
    </li>

    <li class="page-item" th:classappend="${posts.last} ? disabled">
      <a class="page-link" th:href="|javascript:gopage(${posts.number}+2)|"
        >Next</a
      >
    </li>
  </ul>
</nav>
<!-- 페이징 끝 -->
```
