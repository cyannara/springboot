## DI(Dependency Injection)

- IoC: 어떤 객체가 사용하는 의존 객체를 직접 만들어 사용하는게 아니라, 외부 컨테이너에서 주입 받아 사용하는 방법
- 프로그램의 소스를 변경하지 않고 의존관계에 있는 클래스를 전환(교체)를 쉽게 만드는 것
- 인터페이스구현 방식과 ApplicationContext 이용

### spring DI

1. 객체생성
2. 의존관계 연결
3. 생명주기 관리(Singleton, Prototype, session, request)

### 실습1 클래스를 교체

```java
public class SamsungTV {

	public void powerOn() {
		System.out.println("삼성 TV--전원 on");
	}
	public void powerOff() {
		System.out.println("삼성 TV--전원 off");
	}
	public void volumeUp() {
		System.out.println("삼성 TV--볼륨 up");
	}
	public void volumeDown() {
		System.out.println("삼성 TV--볼륨 down");
	}
}

public class LgTV {

	public void powerOn() {
		System.out.println("LG TV--전원 on");
	}
	public void powerOff() {
		System.out.println("LG TV--전원 off");
	}
	public void soundUp() {
		System.out.println("LG TV--볼륨 up");
	}
	public void soundDown() {
		System.out.println("LG TV--볼륨 down");
	}
}

public class DITest {

	@Test
	public void test() {

		SamsungTV tv = new SamsungTV();   //LgTV로 교체해보세요

		tv.powerOn();
		tv.volumeUp();
		tv.volumeDown();
		tv.powerOff();

	}
}
```

### 실습2 인터페이스를 이용한 클래스 구현

- 기본적으로 구현해야 할 메소드에 대한 규격을 지정하고 강제적으로 구현

인터페이스 선언

- Refactor -> Extract Interface... -> 인터페이스 이름 입력하고 members는 모두 선택

```java
public interface TV {
	void powerOn();
	void powerOff();
	void volumeUp();
	void volumeDown();
}
```

TV 인터페이스 상속받도록 SamsungTV, LgTV 수정

```java
public class SamsungTV implements TV { ...  }
public class LgTV implements TV { ...  }


public class DITest {

	@Test
	public void test() {

		TV tv = new SamsungTV();   //LgTV로 교체해보세요

		tv.powerOn();
		tv.volumeUp();
		tv.volumeDown();
		tv.powerOff();

	}
}
```

### 실습3 BeanFactory

```java
public class BeanFactory {

  public static Object getBean(String beanName){
        if(beanName.equals("sm")){
            return new SamsungTV();
        } else if(beanName.equals("lg")){
            return new LgTV();
        }
        return null;
    }

}
```

```sh
D:\eGovFrameDev-4.3.0-64bit\workspace-egov\sp01\target\classes>java com.yedam.app.factory.App  lg
===> LgTV
LgTV powerOn.
LgTV volumeUp.
LgTV volumeDown.
LgTV powerOff.

D:\eGovFrameDev-4.3.0-64bit\workspace-egov\sp01\target\classes>java com.yedam.app.factory.App  samsung
===> SamsungTV
SamsungTV powerOn
SamsungTV volumeUp
SamsungTV volumeDown
SamsungTV powerOff

D:\eGovFrameDev-4.3.0-64bit\workspace-egov\sp01>mvn package
[INFO] Scanning for projects...
[INFO]
[INFO] -------------------------< com.yedam.app:sp01 >-------------------------
[INFO] Building demo 0.0.1-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
...
[INFO] Running com.yedam.app.demo.AppTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.022 s -- in com.yedam.app.demo.AppTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO]
[INFO] --- jar:3.4.1:jar (default-jar) @ sp01 ---
[INFO] Building jar: D:\eGovFrameDev-4.3.0-64bit\workspace-egov\sp01\target\sp01-0.0.1-SNAPSHOT.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.077 s
[INFO] Finished at: 2025-05-23T07:44:51+09:00
[INFO] ------------------------------------------------------------------------

D:\eGovFrameDev-4.3.0-64bit\workspace-egov\sp01>java -cp  ./target/sp01-0.0.1-SNAPSHOT.jar  com.yedam.app.factory.App  lg
===> LgTV
LgTV powerOn.
LgTV volumeUp.
LgTV volumeDown.
LgTV powerOff.

D:\eGovFrameDev-4.3.0-64bit\workspace-egov\sp01>java -cp  ./target/sp01-0.0.1-SNAPSHOT.jar  com.yedam.app.factory.App  samsung
===> SamsungTV
SamsungTV powerOn
SamsungTV volumeUp
SamsungTV volumeDown
SamsungTV powerOff
```

### 실습4 DI 실습

`@Component` 애노테이션을 이용하여 applicationContext에 bean 등록

```java
@Component
public class SamsungTV
```

`@Autowired` 애노테이션을 이용하여 객체 주입

```java
public class DITest {

	@Autowired TV tv;

	@Test
	public void test() {
		tv.powerOn();
	}
}
```

```java
  @Autowired  ApplicationContext context;

  @Test
  public void test() {
    TV tv = context.getBean(TV.class);
```

### 실습5 의존관계에 있는 객체 주입

Speaker 인터페이스 선언

```java
public interface Speaker {
	void volumeUp();
	void volumeDown();
}
```

SonySpeaker 구현 클래스

```java
public class SonySpeaker implements Speaker {

	public SonySpeaker(){		System.out.println("Sony Speaker 생성");	}

	@Override
	public void volumeUp(){
		System.out.println("Sony Speaker 소리 올림");
	}

	@Override
	public void volumeDown(){
		System.out.println("Sony Speaker 소리 내림");
	}
}
```

AppleSpeaker 구현 클래스

```java
public class AppleSpeaker implements Speaker {

	public AppleSpeaker() {
		System.out.println("Apple Speaker 생성");
	}

	@Override
	public void volumeUp() {
		System.out.println("Apple Speaker 소리 올림");
	}

	@Override
	public void volumeDown() {
		System.out.println("Apple Speaker 소리 내림");
	}
}
```

DI

1. 필드주입

   ```java
     @Autowired
     private Speaker speaker;
   ```

2. setter 주입

   ```java
     @Autowired
     public void setSpeaker(Speaker speaker) {
       this.speaker = speaker;
     }
   ```

3. **생성자 주입**

   ```java
     private Speaker speaker;

   //  @Autowired    Unnecessary `@Autowired` annotation [JAVA_AUTOWIRED_CONSTRUCTOR]
     public void setSpeaker(Speaker speaker) {
       this.speaker = speaker;
     }
   ```

DI : 롬복

1. @Setter 애노테이션

   ```java
     @Setter(onMethod_ = {@Autowired})
     private Speaker speaker;
   ```

2. **`final` 과 `@RequiredArgsConstructor` 애노테이션**

   ```java
   @RequiredArgsConstructor
   public class LGTV implements TV {

     private final Speaker speaker;
   ```

### 실습6 DI : java 기반 설정

- `@Configuration`이 붙으면, AnnotationConfigApplicationContext 에 파라미터로 넘긴 값은 스프링 빈으로 등록된다.
- @Bean만 사용해도 스프링 빈으로 등록되지만, 싱글톤을 보장하지 않는다.

  ```java
  @Configuration
  public class JavaConfig {
    @Bean
    Speaker speaker() {
      return new AppleSpeaker();
    }

    @Bean
    TV tv() {
      return new LgTV(speaker());
    }
  }
  ```

### 실습7 di test : Restaurant 클래스, Chift 클래스

```java
public class Restaurant {
  private Chef chef;
}

```

```java
public class Chef {

}
```

```java
public class DITest {

  @Autowired
  ApplicationContext context;

  @Autowired TV tv;

  @Autowired Restaurant restaurant;

  @Test
  public void Restauranttest() {
    restaurant.getChef().getName();
  }
}
```
