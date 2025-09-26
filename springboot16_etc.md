## Spring Boot Actuator

애플리케이션을 운영 환경(운영 서버, 배포 후 상태 모니터링 등)에서 모니터링하고 관리하는 도구로서 운영 중인 애플리케이션을 관찰하고, 문제를 조기에 발견하며, 안정적인 서비스 운영을 도와준다

1. 애플리케이션 상태 모니터링

   - health 엔드포인트를 통해 DB, 캐시, 외부 API, 디스크 상태 등 서비스의 전반적인 헬스 체크 가능
   - 외부 모니터링 시스템(예: Prometheus, Grafana, AWS CloudWatch)과 연동 가능

2. 운영 지표(메트릭) 제공

   - JVM 메모리 사용량, GC(Garbage Collection) 횟수, 스레드 상태, HTTP 요청 수, 응답 시간 등 성능 지표 수집
   - 마이크로서비스 환경에서 서비스의 상태를 숫자로 관찰하고 알람 설정 가능

3. 애플리케이션 관리

   - env 엔드포인트로 환경 변수 확인
   - loggers 엔드포인트로 런타임에 로깅 레벨 변경
   - beans 엔드포인트로 현재 로드된 빈(bean) 목록 확인
   - mappings 엔드포인트로 컨트롤러 URL 매핑 정보 확인
   - health
   - info
   - metrics
   - loggers

4. DevOps / 운영 자동화

   - CI/CD 파이프라인에서 애플리케이션 상태 점검 후 배포 자동화에 활용
   - 클라우드 오케스트레이션(Kubernetes readiness/liveness probe)에서 사용

#### 라이브러리 추가

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

#### properties 설정

```properties
# =============================
# Actuator 기본 설정
# =============================

# 어떤 엔드포인트를 노출할지 지정 (쉼표 구분)
management.endpoints.web.exposure.include=health,info,metrics,beans,env,loggers,mappings

# health 엔드포인트 세부 정보 항상 보이게
management.endpoint.health.show-details=always

# Actuator 엔드포인트 접두사 (기본: /actuator)
management.endpoints.web.base-path=/actuator

# 특정 포트에서만 actuator 노출 (운영에서 보안상 분리할 때 사용)
# management.server.port=9001

# info 엔드포인트에 노출할 정보
info.app.name=DemoApplication
info.app.description=Spring Boot Actuator Example
info.app.version=1.0.0

# 로깅 레벨 확인 및 변경 가능하도록
management.endpoint.loggers.enabled=true
```

https://docs.spring.io/spring-boot/api/rest/actuator/index.html

실습

```bash
curl http://localhost:8080/actuator/health
curl -i http://localhost:8080/actuator/health
curl -iv http://localhost:8080/actuator/health
```
