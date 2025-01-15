### REST 관련 애노테이션

| 애너테이션      | 기능능                                             |
| :-------------- | :------------------------------------------------- |
| @RestController | Controller가 REST 방식을 처리하기 위한 것임을 명시 |
| @ResponseBody   | 데이터 자체를 전달                                 |
| @PathVariable   | URL 경로에 있는 값을 파라미터로 추출               |
| @CrossOrigin    | Ajax의 크로스 도메인 문제를 해결                   |
| @RequestBody    | JSON 데이터를 원하는 타입으로 바인딩               |
|                 |                                                    |

### Jackson-databind 라이브러리

- Jackson-databind
- XML : jackson-dataformat-xml

### @RestController

- 문자열
- JSON
- XML

### ddd

- REST
- SPA
- MSA
- HATEOAS
- SWAGGER
-

HATEOAS (Hypermidia As The Engine Of Application State)
HATEOAS(일명 헤이티오스)는 API를 실제로 "RESTful"하게 만드는 REST Appilcation Architecture의 제약 조건이다.기본적으로 요청에 대해 서버는 응답에 데이터만 클라이언트에게 보내는데,HATEOAS를 사용하면 응답에 데이터뿐만 아니라 해당 데이터와 관련된 요청에 필요한 URI를 응답에 포함하여 반환하며, 이는 REST API를 사용하는 클라이언트가 전적으로 서버와 동적인 상호작용이 가능하도록 해준다.

Hypermidia
As
The
Engine
Of
Application
State

HATEOAS : 추가 상태 정보를 제공

Swagger : REST API Documentation을 위해 사용

Actuator : 내장 톰캣의 상태를 모니터링 할 수 있는 기능. Actuator는 스프링 부트가 가지고 있는 기본적인 기능에다가 모니터링과 로깅 정보와 같은 것을 제어할 수 있도록 제공하는 서포트 라이브러리이다.
사용하고 있는 localhost 포트 번호에 /actuator 경로로 접속하면 위와 같은 정보를 확인할 수 있다. 해당 프로젝트의 상태를 체크 할수있는 URL의 정보를 담고 있다.

JWT
