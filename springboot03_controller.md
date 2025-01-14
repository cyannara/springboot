### Controller 작성

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
