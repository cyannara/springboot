### AOP
- AOP(aspect-Oriented Programming) : 관점지향프로그래밍.
- OOP에서 모듈화의 핵심 단위가 `클래스`라고 한다면, AOP에서는 핵심 단위가 `관점`이다.
- Spring AOP는 로깅, 보안, 트랜잭션 관리 등과 같은 공통적인 관심사를 모듈화 하여 코드 중복을 줄이고 유지 보수성을 향상.
- `핵심 관심사`는 각 객체가 가져야 할 본래의 기능이며, `공통 관심사`는 여러 객체에서 공통적으로 사용되는 코드
- 여러 개의 클래스에서 반복해서 사용하는 코드가 있다면 **해당 코드를 모듈화 하여 공통 관심사로 분리합니다.** 이렇게 분리한 공통 관심사를 Aspect로 정의하고 Aspect를 적용할 메소드나 클래스에 Advice를 적용하여 공통 관심사와 핵심 관심사를 분리할 수 있습니다. 이렇게 AOP에서는 공통 관심사를 별도의 모듈로 분리하여 관리하며, 이를 통해 코드의 재사용성과 유지 보수성을 높일 수 있습니다.


### 주요 용어
|||
|:-- |:--|
|용어|설명|
|Aspect| 공통기능(부가기능)을 모듈화|
|Target| Aspect를 적용하는 곳|
|Advice| Aspect의 기능을 정의. 실질적인 부가기능을 담은 구현체|
|Join Pont|Advice가 적용될 위치. 확인(Aspect가 적용될 수 있는 시점( 메서드 실행전, 후))|
|Pont Cut| Advice를 적용할 메소드의 범위를 지정. JoinPoint의 상세한 스펙을 정의. 구체적으로 Advice가 실행될 지점|
|||

### 주요 어노테이션
|||
|:-- |:--|
|@Aspect| 해당 클래스를 Aspect로 사용|
|@PointCut|Advice를 적용할 메서드를 필터링|

###  Aspect 실행 시점을 지정
|||
|:-- |:--|
|@Before|대상(서비스) 메서드가 실행되기 전에 Advice를 실행|
|@AfterRetruning|대상(서비스) 메서드가 정상적으로 실행되고 반환된 경우 Advice를 실행|
|@AfterThrowing|대상(서비스) 메서드가 예외가 발생한 경우 Advice를 실행|
|@After|대상(서비스) 메서드가 실행된 후에 Advice를 실행|
|@Around|대상(서비스) 메서드가 실행 전, 후에 Advice를 실행|

### ■ 라이브러리 의존성 추가

```xml
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-aop -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```



### ■ Aspect
```java
package com.yedam.app;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

// Advice 클래스 = 공통코드
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