## JPA

### JPA 오라클 계정 생성
관리자 권한으로 실행  
```
ALTER SESSION SET "_ORACLE_SCRIPT"=true;
create user jpa identified by jpa;
grant resource, connect to jpa;
ALTER USER jpa DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS;
```

### JPA 환경설정

Oracle

```properties

#oracle
#spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
#spring.datasource.url=jdbc:oracle:thin:@localhost:1521/xe
#spring.datasource.username=hr
#spring.datasource.password=hr

#jpa
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format-sql=true

```

Mysql

```properties

#mysql
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=hr
spring.datasource.password=hr
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

#jpa
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
#spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format-sql=true
```

### 등록하기

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
public class Posts{

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

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {

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

### 페이징

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
