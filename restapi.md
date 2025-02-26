## 스프링부트가 제공하는 API관련 유틸리티 ( HATEOAS, Swagger, Actuator)

### HATEOAS
Hypermedia As The Engine of Application State  
Hypermedia(링크)를 통해서 다음 가능한 행동(action)에 대한 정보를 응답 본문에 넣어주어야 한다.  추가정보를 제공  

- REST 성숙도 모델(Richardson) [참조](https://g4daclom.tistory.com/163)
  - Level 0: 
  - Level 1: 리소스별로 고유한 URI를 사용  
  - Level 2: HTTP 메소드 원칙 준수  
  - Level 3: HATEOAS 원칙 준수  
  - Level 4

### Swagger
Swagger : REST API Documentation을 위해 사용  

### Actuator
Actuator : 내장 톰캣의 상태를 모니터링 할 수 있는 기능  


## HATEAOS
1. add dependency 

```xml
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-hateoas -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-hateoas</artifactId>
    <version>3.4.3</version>
</dependency>
```

2. Controller에 HATEAOS 관련 코드 추가



| 측면 | Swagger | HATEOAS |
| :-- | :-- |:----- |
| API 문서화 | Swagger는 소비자가 사용 가능한 엔드포인트, 요청 매개변수 및 응답을 사전에 이해할 수 있도록 UI를 갖춘 자세하고 인간이 읽을 수 있는 API 문서를 제공합니다. | HATEOAS는 서버에서 응답 내에 반환된 하이퍼미디어 링크에 의존하므로 문서화가 더 암묵적입니다. 따라서 소비자는 생성을 통해 이러한 링크를 통해 동적으로 작업을 발견합니다. |
| 클라이언트 측 구현 | 클라이언트는 일반적으로 Swagger 사양을 기반으로 생성되거나 작성됩니다. API의 구조는 사전에 알려져 있으며, 클라이언트는 미리 정의된 경로에 따라 요청을 할 수 있습니다. | HATEOAS 클라이언트는 응답 내의 하이퍼미디어 링크를 통해 사용 가능한 작업을 발견하여 API와 동적으로 상호작용합니다. 클라이언트는 전체 API 구조를 사전에 알 필요가 없습니다. |
| 유연성 | Swagger는 미리 정의된 엔드포인트와 일관된 API 구조를 기대하고 있어 보다 경직되어 있습니다. 이는 문서 또는 사양을 업데이트하지 않고 API를 발전시키기 어렵게 만듭니다. | HATEOAS는 API가 발전할 수 있도록 더 큰 유연성을 제공하여 하이퍼미디어 기반 응답을 변경하더라도 기존 클라이언트에 영향을 주지 않습니다. |
| 소비자 용이성 | 자동 생성된 문서 또는 API 사양에서 직접 클라이언트 코드를 생성하는 도구에 의존하는 소비자에게는 쉽습니다. | 소비자에게는 더 복잡합니다. 이들은 응답을 해석하고 하이퍼미디어 링크를 따라 개별적으로 작업을 발견해야 합니다. |
| API 발전 | API 구조의 모든 변경은 Swagger 사양을 업데이트하고, 클라이언트 코드를 재생성하며, 사용자가 사용하도록 배포해야 합니다. | HATEOAS는 클라이언트가 하이퍼미디어를 통해 API를 탐색하므로 API가 발전할 때 업데이트가 덜 필요합니다. |
| 버전 관리 | Swagger는 일반적으로 명시적인 버전 관리와 함께 여러 버전의 API를 별도로 유지 관리해야 합니다. | HATEOAS는 클라이언트가 제공된 링크를 따라 동적으로 발전하므로 엄격한 버전 관리 없이 진화할 수 있습니다. |
||||

HATEOAS는 응답에 포함된 하이퍼미디어 링크를 통해 클라이언트를 API 상호작용으로 동적으로 안내하는 데 중점을 두며, Swagger (또는 OpenAPI)는 API의 구조, 엔드포인트 및 작업을 설명하는 정적이고 인간이 읽을 수 있으며 기계가 읽을 수 있는 API 문서를 제공합니다