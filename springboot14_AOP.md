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

|   어노테이션        |    설명                             |
| :-------- | :------------------------------ |
| @Aspect   | 해당 클래스를 Aspect로 사용     |
| @PointCut | Advice를 적용할 메서드를 필터링 |

### Aspect 실행 시점을 지정

|   어노테이션        |    설명                             |
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

## 예외 처리

![alt text](/images/aop_exceptionHandler.png)

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

스프링 애플리케이션 전체에서 예외 처리 메서드를 선언할 수 있는 특수한 스프링 빈.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(MaxUploadSizeExceededException ex) {
        return "업로드 가능한 파일 크기를 초과했습니다.";
    }
}
```

### @RestControllerAdvice

@RestControllerAdvice = @ControllerAdvice + @ResponseEntity 기능을 합친 어노테이션
예외처리 메서드가 리턴하는 객체는 HttpMessageConverter로 마셜링되어 JSON 메시지가 클라이언트에 전달 됨

```java
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<String> handleBadRequestException(BadRequestException ex){
		System.out.println("Error Message " + ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
```

### error page

/templates/error.html

## 어노테이션 만들기

어노테이션을 만드는 것은 마치 인터페이스를 정의하고 해당 인터페이스를 구현하는 클래스를 작성하는 것과 비슷함.

`@interface`를 시용하여 이 클래스 이름이 어노테이션으로 사용된다는 설정을 함.

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //런타임에 리플렉션으로 사용 가능
@Target(ElementType.METHOD)  //메서드에만 적용 가능
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
