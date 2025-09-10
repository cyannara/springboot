# 컨트롤러

## 컨트롤러 애너테이션

### Spring MVC 컨트롤러 주요 애너테이션 정리

| 애너테이션                          | 설명                                                                                                       | 예시                                         |
| :---------------------------------- | :--------------------------------------------------------------------------------------------------------- | :------------------------------------------- |
| **@Controller**                     | MVC 패턴의 컨트롤러 클래스임을 표시. 주로 뷰(JSP, Thymeleaf 등)를 반환할 때 사용.                          |                                              |
| **@RestController**                 | `@Controller` + `@ResponseBody` 조합. 뷰가 아닌 JSON/XML 등 데이터를 직접 반환하는 REST API 전용 컨트롤러. |                                              |
| **@RequestMapping**                 | 요청 URL과 메서드를 매핑. 클래스나 메서드 레벨에 사용 가능.                                                | `@RequestMapping("/users")`                  |
| **@PostMapping**, **@GetMapping**   | `@RequestMapping(method=...)`의 축약형. 각각 POST, GET 요청을 처리.                                        |                                              |
| **@DeleteMapping**, **@PutMapping** | REST API에서 DELETE, PUT 요청을 처리할 때 사용.                                                            |                                              |
| **@RequestParam**                   | 요청 파라미터(쿼리스트링, form-data)를 매개변수에 바인딩.                                                  | `/user?id=1` → `@RequestParam("id") Long id` |
| **@RequestPart**                    | Multipart 요청에서 특정 파일이나 JSON 파트를 바인딩할 때 사용.                                             | `@RequestPart("file") MultipartFile file`    |
| **@PathVariable**                   | URL 경로의 변수를 매핑.                                                                                    | `/user/10` → `@PathVariable Long id`         |
| **@RequestBody**                    | 요청 본문(JSON/XML)을 Java 객체로 변환해서 매개변수에 바인딩.<br>주로 POST/PUT API에서 사용.               |                                              |
| **@ResponseBody**                   | 메서드 반환값을 뷰가 아닌 HTTP 응답 바디에 직접 담아 반환.<br>JSON/XML로 직렬화됨.                         |                                              |

### Controller에서 자주 쓰는 추가 애너테이션

| 애너테이션              | 설명                                                                     | 예시                                                         |
| ----------------------- | ------------------------------------------------------------------------ | :----------------------------------------------------------- |
| **@ModelAttribute**     | 요청 파라미터를 객체에 자동 바인딩하고, 뷰 렌더링 시 모델에 자동 등록됨. |                                                              |
| **@SessionAttributes**  | 특정 모델 속성을 세션에 저장하여 여러 요청 간 공유할 수 있게 함.         |                                                              |
| **@CookieValue**        | 요청 쿠키 값을 매개변수로 주입받음.                                      | `@CookieValue("JSESSIONID") String sessionId`                |
| **@RequestHeader**      | 요청 헤더 값을 매개변수로 주입받음.                                      | `@RequestHeader("User-Agent") String userAgent`              |
| **@CrossOrigin**        | CORS 허용 설정. 특정 컨트롤러/메서드에서 외부 도메인 요청 허용 가능.     |                                                              |
| **@Valid / @Validated** | 요청 데이터를 검증할 때 사용.                                            | `public String save(@Valid User user, BindingResult result)` |
| **@ExceptionHandler**   | 컨트롤러 내 특정 예외를 처리할 메서드를 지정.                            |                                                              |
| **@ControllerAdvice**   | 전역적으로 예외 처리, 바인딩 설정 등을 적용하는 클래스에 사용.           |                                                              |
| **@InitBinder**         | 컨트롤러 내에서 데이터 바인딩/포맷팅 규칙을 직접 정의할 때 사용.         |                                                              |
| **@ResponseStatus**     | 메서드 실행 결과에 대해 HTTP 상태 코드를 지정.                           | `@ResponseStatus(HttpStatus.CREATED)`                        |

## @Controller와 @RestController

<img src="images/controller01.png" width="550">
<img src="images/controller02.png" width="550">

## Controller 작성

1. 컨트롤러 애노테이션
2. 서비스 객체 인젝션
3. 핸들러 메서드와 @XxxMapping
4. bean validation
5. 요청 파라미터 가져오기
6. 서비스 호출
7. 뷰에 데이터 전달하기

```java
@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

	private BoardService service;

	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		model.addAttribute("list", service.getList(cri));
    return "board/list";
	}

```

### Controller 실습

SampleController

```java
@Controller
@RequestMapping("/sample/*")
@Log4j2
public class SampleController {

	@RequestMapping("")
	public String basic() {
		return "sample";
	}
}
```

templates/sample.html

```html
<body>
  sample
</body>
```

application.properties

```
logging.level.org.springframework=debug
```

Console log 확인

```
web.servlet.DispatcherServlet  : GET "/sample/aaa", parameters={}
RequestMappingHandlerMapping   : Mapped to com.example.demo.SampleController#basic()
ContentNegotiatingViewResolver : Selected 'text/html'
```

### bean validation

Spring Boot는 사용자 정의 유효성 검사기와의 완벽한 통합을 지원하지만 유효성 검사를 수행하는 사실상의 표준은 Bean 유효성 검사 프레임 워크의 참조 구현인 Hibernate Validator 입니다.

reference

https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html  
https://docs.jboss.org/hibernate/validator/8.0/reference/en-US/html_single/#section-builtin-constraints

라이브러리 dependency 추가

```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
```

bean에 validation 적용

```java
public class User {

    @NotNull(message = "Name cannot be null")
    private String name;

    @AssertTrue(message = "Working must be true")
    private boolean working;

    @Size(min = 10, max = 200, message
      = "About Me must be between 10 and 200 characters")
    private String aboutMe;

    @Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 150, message = "Age should not be greater than 150")
    private int age;

    @Email(message = "Email should be valid")
    private String email;
}
```

Bean Validation 주요 애노테이션

| JSR 애너테이션             | 용도                                                                 |
| :------------------------- | :------------------------------------------------------------------- |
| @NotNull                   | null 아닌지 검증                                                     |
| @AssertTrue                | Boolean 타입의 필드 혹은 메서드의 반환 값이 true 인지 검증           |
| @NotBlank                  | 문자값이 null, 빈문자(""), 공백문자(" ")가 아닌지 검증               |
| @Size                      | String, Collection, Map, and array 의 크기                           |
| @Min                       | `정수`값이 지정된 크기 이상인지 검증                                 |
| @Max                       | 정수값이 지정된 크기 이하인지 검증                                   |
| @DecimalMax, @DecimalMin   | `실수`값이 지정된 크기 이하인지 검증                                 |
| @Email                     | 유효한 이메일인지 검증                                               |
| @NotEmpty                  | String, Collection, Map 또는 Array 가 null 또는 empty 가 아닌지 검증 |
| @Positive, @PositiveOrZero | 양수. 0을 포함하는 양수인지 검증                                     |
| @Negative, @NegativeOrZero | 음수. 0을 포함하는 음수인지 검증                                     |
| @Past, @PastOrPresent      | 날짜 값이 과거인지, 또는 현재를 포함한 과거인지 검증                 |
| @Future, @FutureOrPresent  | 날짜 값이 미래 또는 현재를 포함한 미래인지 검증                      |
| @Pattern                   | 지정한 정규표현식에 맞는지 검증                                      |
|                            |                                                                      |

테스트 코드

```java
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class UserValidationTest {

	private Validator validator;

	@BeforeEach
	void setUp() {
	    validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	void test() {
		User user = new User();
		user.setName("test-name");
		user.setWorking(true);
		user.setAboutMe("test-about-me");
		user.setAge(24);
		user.setEmail("test.baeldung.ut");

		Set<ConstraintViolation<User>> violations = validator.validate(user);
		violations.forEach(err->log.debug(err.getPropertyPath().toString() + ": " + err.getMessage()));
		assertTrue(violations.isEmpty());
	}
}
```

controller 적용

BoardDTO
bean에 `@NotBlank` 적용

```java
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO {
	 private Long bno;

	 @NotBlank
	 private String title;
	 @NotBlank
	 private String content;
	 @NotBlank
	 private String writer;
	 private Date regdate;
}
```

BoardController
`@Validated` BoardDTO board, `BindingResult bindingResult`를 컨트롤러 핸들러 인수에 추가

```java
	@GetMapping("/register")
	public void registger(BoardDTO board) {}


	@PostMapping("/register")
	public String register(@Validated BoardDTO board,
                         BindingResult bindingResult,
                         RedirectAttributes rttr) {
		if(bindingResult.hasErrors()) {
			return "board/register";
		}
		log.info("register: " + board);
		service.register(board);

		rttr.addFlashAttribute("result", true);
		return "redirect:/board/list";
	}
```

board/list.html
error 메시지를 출력할 곳에 `th:errors` 지정

```html
<div class="error" th:errors="${boardDTO.title}"></div>
```

```html
<style>
  .error {
    color: red;
  }
</style>

<form action="register" method="post">
  <div class="mb-3">
    <label for="title" class="form-label">제목</label>
    <input
      type="text"
      class="form-control"
      name="title"
      placeholder="제목입력"
      th:value="${boardDTO.title}"
    />
    <div class="error" th:errors="${boardDTO.title}"></div>
  </div>
</form>
```
