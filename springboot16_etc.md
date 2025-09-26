## Spring Batch

배치 작업이란게 데이터베이스나 api 또는 파일을 읽는 작업을 진행하고, 필요한 내용을 처리한 이후에 다시 파일이나 데이터베이스 같은 곳에 write 하는 작업을 진행.
스프링 배치를 활용하여 개발을 한다면 Job 과 Step, Step의 있는 ItemReader, ItemProcessor, ItemWriter 항목들을 활용하여 개발을 함.

https://docs.spring.io/spring-batch/reference/spring-batch-intro.html

비즈니스 시나리오

- Spring Batch는 다음과 같은 비즈니스 시나리오를 지원합니다.
- 주기적으로 일괄처리 프로세스를 커밋합니다.
- 동시 일괄 처리: 작업의 병렬 처리.
- 단계적 엔터프라이즈 메시지 중심 처리.
- 대규모 병렬 배치 처리.
- 실패 후 수동 또는 예약된 재시작.
- 종속 단계의 순차적 처리(워크플로 기반 배치로 확장 가능).
- 부분 처리: 레코드 건너뛰기(예: 롤백 시).
- 소규모 배치 크기나 기존 저장 프로시저 또는 스크립트가 있는 경우의 전체 배치 트랜잭션입니다.

![alt text](image.png)

JobRepository  
JobRepository는 배치 수행을 위한 데이터 (시작 시간, 종료 시간, 상태, 읽기/쓰기, 회수 등) 및 Job의 상태관리를 담당합니다. JobRepository는 스프링 배치 내의 잡런쳐, 잡, 스탭과 같이 컴포넌트들이 데이터를 공유를 하게 됩니다.  
JobLauncher  
잡런처는 잡을 실행해주는 역할을 합니다. 잡이 실행이 되면 JobRepository에는 작업의 따른 데이터들이 갱신이 되겠습니다.  
Job과 Step  
Job은 하나의 배치 작업입니다. 배치 작업을 하나 수행한다고 하면 Job을 수행 하는 개념입니다. 그리고 하나의 Job 안에는 한개의 Step 또는 여러개의 Step이 있을 수 있습니다. Step은 하나의 배치 작업 안에, 여러 단계가 있다면 이걸 나눠 놓은 개념입니다. Step은 ItemReader, ItemProcessor, ItemWriter가 있어서 무언가를 읽고 처리하고 쓰기를 진행하겠습니다.

#### 자동 실행 방지

```
spring.batch.job.enabled = false
```

JobLauncherCommandLineRunner가 활성화 되어 있어서 따로 스케쥴을 걸지 않아도 애플리케이션 시작 시 컨텍스트 내 Job Bean을 찾아 자동으로 실행하고 JobParameters가 없으면 기본으로 생성함.

@BatchAutoConfiguration : 기본적으로 Batch 작업에 필요한 기본 객체들을 빈으로 등록

### actuator job 확인

```
http://localhost:85/actuator/metrics/spring.batch.job
```

Boot 3.x부터는 Actuator의 Batch 전용 엔드포인트가 사라지고, 대신 Micrometer Metrics 방식으로 모니터링하도록 바뀌었습니다. actuator/batch가 deprecated 됨

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
