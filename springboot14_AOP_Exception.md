# AOP

### AOP 란

- AOP(aspect-Oriented Programming) : 관점지향프로그래밍.
- OOP에서 모듈화의 핵심 단위가 `클래스`라고 한다면, AOP에서는 핵심 단위가 `관점`이다.
- Spring AOP는 로깅, 보안, 트랜잭션 관리 등과 같은 공통적인 관심사를 모듈화 하여 코드 중복을 줄이고 유지 보수성을 향상.
- `핵심 관심사`는 각 객체가 가져야 할 본래의 기능이며, `공통 관심사`는 여러 객체에서 공통적으로 사용되는 코드
- 여러 개의 클래스에서 반복해서 사용하는 코드가 있다면 **해당 코드를 모듈화 하여 공통 관심사로 분리합니다.** 이렇게 분리한 공통 관심사를 Aspect로 정의하고 Aspect를 적용할 메소드나 클래스에 Advice를 적용하여 공통 관심사와 핵심 관심사를 분리할 수 있습니다. 이렇게 AOP에서는 공통 관심사를 별도의 모듈로 분리하여 관리하며, 이를 통해 코드의 재사용성과 유지 보수성을 높일 수 있습니다.

### 주요 용어

| 용어        | 설명                                                                                         |
| :---------- | :------------------------------------------------------------------------------------------- |
| Aspect      | 공통기능(부가기능)을 모듈화                                                                  |
| Target      | 공통모듈을 적용할 대상(서비스)                                                               |
| Advice      | Aspect의 기능을 정의. 실질적인 부가기능을 담은 구현체                                        |
| Pont Cut    | Advice를 적용될 위치를 설정. JoinPoint의 상세한 스펙을 정의. 구체적으로 Advice가 실행될 지점 |
| Join Pont   | Advice가 적용될 위치. 확인(Aspect가 적용될 수 있는 시점( 메서드 실행전, 후))                 |
| weaving     | 조인포인트에 실행할 코드인 어드바이스를 끼워넣는 과정                                        |
| 프록시 객체 | 스프링 AOP는 관점 클래스와 대상 클래스의 기능을 조합하기 위해 동적으로 프록시 객체를 만듬.   |

### 주요 어노테이션

| 어노테이션 | 설명                            |
| :--------- | :------------------------------ |
| @Aspect    | 해당 클래스를 Aspect로 사용     |
| @PointCut  | Advice를 적용할 메서드를 필터링 |

### Aspect 실행 시점을 지정

| 어노테이션      | 설명                                                                |
| :-------------- | :------------------------------------------------------------------ |
| @Before         | 대상(서비스) 메서드가 실행되기 전에 Advice를 실행                   |
| @AfterRetruning | 대상(서비스) 메서드가 정상적으로 실행되고 반환된 경우 Advice를 실행 |
| @AfterThrowing  | 대상(서비스) 메서드가 예외가 발생한 경우 Advice를 실행              |
| @After          | 대상(서비스) 메서드가 실행된 후에 Advice를 실행                     |
| @Around         | 대상(서비스) 메서드가 실행 전, 후에 Advice를 실행                   |

### ■ 라이브러리 의존성 추가

스프리 이니셜라이즈를 통해 추가할 수 없음. pom.xml에 직접 추가

```xml
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-aop -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

### Aspect

```java
package com.yedam.app;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

// Advice 클래스 = 공통로직
// Aspect 클래스 = Advice + pointcut
@Log4j2
@Component
@Aspect
public class BeforeAdvice {

	@Pointcut("execution(* com.yedam..*Impl.*(..))")
	public void  allpointcut() {}

	@Before("allpointcut()")
	public void beforeLog(JoinPoint jp) {
		//메서드명
		String methodName = jp.getSignature().getName();
		//methodName += ":" + jp.toLongString();
		//methodName += ":" + jp.toShortString();

		//인수(argument)
		Object[] args = jp.getArgs();
		Object arg1 =  (args != null && args.length>0 ? args[0] : "") ;
		log.debug("[사전처리로그] beforeLog \n[" + methodName +"] [arg] " + arg1);
	}
}
```

## 트랜잭션

- @Transactional
- 서비스 계층에서 사용: 컨트롤러에 @Transactional을 선언해도 AOP 프록시로 인해 동작은 하지만, 트랜잭션은 비즈니스 로직 단위로 관리해야 하므로 서비스 계층에 선언하는 것이 원칙입니다.

### 컨트롤러에서 사용하면 안되는 이유

- 컨트롤러에서는 json파싱, 요청검증, 인증처리, view 랜더링등 여러 작업을 진행하는데 트랜잭션을 걸게되면 DB Lock이 오래 유지됨
- 서비스 재사용성 붕괴(서비는 트랜잭션 처리가 없음)
- 예외처리 문제. 롤백 타이밍이 꼬일 수 있음. 서비스는 RuntimeException 발생 시 롤백을 하는데 컨트롤러에서는 @ExceptionHandler나 ControllerAdvice가 발생하면서 롤백타이밍이 꼬일 수 있음

## 어노테이션 만들기

어노테이션을 만드는 것은 마치 인터페이스를 정의하고 해당 인터페이스를 구현하는 클래스를 작성하는 것과 비슷함.

`@interface`를 시용하여 이 클래스 이름이 어노테이션으로 사용된다는 설정을 함.

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //어느 시점까지 메모리를 가져 갈것인지 설정(런타임에 리플렉션으로 사용 가능)
@Target(ElementType.METHOD)  //이 어노테이션이 부착될 수 있는 타입(메서드에만 적용 가능)
public @interface  PrintExecutionTime {

}
```

Advice 클래스 선언 : 내부 로직 구현

```java
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("@annotation(PrintExecutionTime)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // 실제 메서드 실행

        long executionTime = System.currentTimeMillis() - start;

        String methodName = joinPoint.getSignature().toShortString();

        log.info("{} 실행 시간: {}ms", methodName, executionTime );

        return result;
    }
}
```

테스트

```java
@SpringBootTest
public class AopTest {

	@Autowired BoardService boardService;

	@Test
	public void test() {
		boardService.regiser();
	}
}
```

## (에러 핸들링](https://docs.spring.io/spring-boot/reference/web/servlet.html#web.servlet.spring-mvc.error-handling)

### error page

- SpringBoot 이전에는 ErrorPageController를 직접 만들어야 했지만 SpringBoot는 동일한 기능을 가진 BasicErrorController 가지고 있다.
- SpringBoot 환경에서는 약속된 이름을 가진 오류페이지를 약속된 위치에 두면, SpringBoot가 알아서 에러 상황에 따라 적절한 오류페이지를 띄운다.
- 오류 정보를 오류페이지에 띄우고 싶다면 띄우고 싶은 오류정보를 설정파일에 설정하면 된다.

오류 정보 출력 여부 설정

```properties
# error
#exception 포함여부
server.error.include-exception=true
# message 포함 여부 (never/always/on_param)
server.error.include-message=always
# trace 포함 여부
server.error.include-stacktrace=on_param
# errors 포함여부
server.error.include-binding-errors=on_param
```

### 범용 error page

- /templates/error.html (thymeleaf)

error 페이지 적용순서

```
templates/
   ├─ error/
   │    └─ 500.html      1
   ├─ error.html         2
static/
   ├─ error/
   │    └─ 500.html      3
   └─ error.html         4


1️⃣ 상태코드 전용 페이지
   templates/error/{status}.html

2️⃣ 범용 error 페이지
   template/error.html

3️⃣ Whitelabel Error Page
```

Whitelabel Error Page - view 페이지 요청
<img src="./images/exception01.png" >

Whitelabel Error Page - ajax 요청  
<img src="./images/exception02.png" >

범용 error page

```html
<head>
  <style>
    div {
      border: 1px red solid;
    }
    .error {
      color: red;
    }
  </style>
</head>
<body>
  <h1>Error Page</h1>
  <a href="#" onclick="history.back()">이전페이지로</a>
  <div>
    <p>상태코드: <span th:text="${status}" class="error"></span></p>
    <p>에러명: <span th:text="${error}" class="error"></span></p>
    <p>메시지: <span th:text="${message}"></span></p>
    <p>요청경로: <span th:text="${path}"></span></p>
    <p>trace: <span th:text="${trace}"></span></p>
  </div>
</body>
```

에러 뷰에서 다음 오류 속성을 표기할 수 있도록 제공 한다.  

- status : HTTP 상태 코드  
- error : 오류 발생 이유  
- message : 예외 메시지  
- path : 오류가 발생했을때 요청한 URL 경로
- timestamp : 오류 발생 시각  
- exception : 예외 클래스 이름  
- errors : BindingResult 예외로 발생한 모든 오류  
- trace : 예외 스택 트레이스  

```
on_param인 경우 trace 파라미터가 넘어오면 에러페이지로 trace를 전달
http://localhost:8080/boarderror?trace=&errors=
```

## 예외 처리

- Global Exception Handler: Controller에 대한 전역적으로 발생할 수 있는 예외를 잡아서 처리할 수 있습니다. 이를 통해 예외 처리 코드를 중앙에서 관리하고 예외에 대한 일관된 응답을 생성할 수 있습니다.
- AOP 개념을 이용하나 구현된 방식이 프록시를 활용하지는 않음

`장점`

- 코드 중복감소: Controller마다 try-catch문을 넣을 필요가 없음
- 유지보수성 향상: 예외 처리 로직이 한 곳에 집중되어 수정이 쉬움
- 일관된응답: 모든 API 에러에 대해 동일한 JSON 구조를 보장
- 가독성: 핵심 비즈니스 로직과 예외 처리 로직의 분리

`작동원리`  
<img src="./images/exception03.png" width="700">

Controller 에서 에러가 발생하여 예외를 던지면 ExceptionHandlerExceptionResolver가 @RestControllerAdvice를 탐지하여 해당 예외를 처리하는 @ExceptionHandler 메소드를 찾음.

`@ControllerAdvice 실행순서`  
<img src="./images/exception04.png" width="700">

`HandlerExceptionResolver의 구현체(어노테이션)`

- @ExceptionHandler
- @ControllerAdvice
- @RestControllerAdvice

### @ExceptionHandler

예외를 처리할 수 있는 메서드를 지정  
컨트롤러 클래스에 선언된 예외처리 메서드는 컨트롤러 클래스의 핸들러 메서드에서 발행한 예외만 처리 가능함.

```java
@Controller
public BoardController {
     @ExceptionHandler(BadRequestException.class)
    public String handleBadRequestException(BadRequestException ex) {
        return "파라미터 바인딩 에러";
    }

    @PostMapping("/register")
    public String register(BoardVO vo){

    }

}
```

### @ControllerAdvice

스프링 애플리케이션 전체에서 예외 처리 메서드를 선언할 수 있는 특수한 스프링 빈으로 전통적인 MVC 웹 애플리케이션에서 HTML View(ViewResolver)를 호출함. Spring이 자동으로 message를 넣어주지 않으므로 직접 Model에 담아야 한다.

```java
@Order(1)
@ControllerAdvice
public class GlobalExceptionHandler {

		//특정 예외처리
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(MaxUploadSizeExceededException ex, Model model) {
			  model.addAttribute("msg", "업로드 가능한 파일 크기를 초과했습니다.");
        return "error";
    }

		// 기본 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllException(Exception e) {
        return ResponseEntity.status(500).body("Internal Server Error");
    }
}
```

### @RestControllerAdvice

@RestControllerAdvice = @ControllerAdvice + @ResponseEntity 기능을 합친 어노테이션  
예외처리 메서드가 리턴하는 객체는 HttpMessageConverter로 마셜링되어 JSON 메시지가 클라이언트에 전달 됨

```java
@Order(2)
@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<String> handleBadRequestException(BadRequestException ex){
		System.out.println("Error Message " + ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	// 나머지 에러
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e) {
			log.error("Exception", e);
			ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
}
```

## 맞춤형 응답: @ExceptionHandler를 사용하여 발생한 예외에 대해 사용자 정의 응답을 생성

### Error code 관리

```java
@Getter
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
```

```java
@Getter
public enum ErrorCode {
    // 인증 && 인가
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "해당 토큰은 유효한 토큰이 아닙니다."),
    NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "A-003", "Authorization Header가 빈 값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "A-004", "인증 타입이 Bearer 타입이 아닙니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A-005", "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-006", "해당 refresh token은 만료됐습니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-007", "해당 토큰은 ACCESS TOKEN이 아닙니다."),
    NO_PERMISSION(HttpStatus.UNAUTHORIZED, "A-008", "권한 없음"),
    FORBIDDEN_ROLE(HttpStatus.FORBIDDEN, "A-009", "해당 Role이 아닙니다."),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "A-001", "회원 정보가 일치하지 않습니다."),

    // 유저
    NOT_EXISTS_USER_ID(HttpStatus.NOT_FOUND, "U-001", "존재하지 않는 유저 아이디입니다."),
    NOT_EXISTS_USER_NICKNAME(HttpStatus.NOT_FOUND, "U-002", "존재하지 않는 유저 닉네임입니다."),
    NOT_EXISTS_USER_EMAIL(HttpStatus.NOT_FOUND, "U-003", "존재하지 않는 유저 이메일입니다."),
    ALREADY_REGISTERED_USER_ID(HttpStatus.BAD_REQUEST, "U-004", "이미 존재하는 유저 아이디입니다."),
    NOT_EXISTS_USER_PASSWORD(HttpStatus.NOT_FOUND, "U-005", "존재하지 않는 유저 비밀번호입니다."),
    INVALID_USER_DATA(HttpStatus.BAD_REQUEST, "U-006", "잘못된 유저 정보입니다."),
    INVALID_ADMIN(HttpStatus.BAD_REQUEST, "U-007", "Admin은 제외 시켜주세요.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
```

### ExceptionHandler

```java
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	// ErrorCode내의 에러
	@ExceptionHandler(BusinessException.class)
	protected Map<String, String> handleBusinessException(BusinessException e) {
			log.error("BusinessException", e);
			Map<String, String> responseMap = new HashMap<>();
			responseMap.put("errorCode", e.getErrorCode().getErrorCode());
			responseMap.put("status", e.getErrorCode().getHttpStatus());
			responseMap.put("message", e.getErrorCode().getMessage());

			return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(responseMap);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<String> handleBadRequestException(BadRequestException ex){
		System.out.println("Error Message " + ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	// 나머지 에러
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e) {
			log.error("Exception", e);
			ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
}
```
