
reference : https://velog.io/@yedam_it/springboot-프로젝트    

### 스프링부트 버전
<pre>
spring boot                   spring            servlet                   tomcat                java           
                                                  6.1                      11                    21    
3.4.2                         6.2.2               
3.3.8                         6.1.16               
3.2.12                        6.1.15              
3.0.13                        6.0.2               6.0                      10                    11 
2.7.18                        5.3.31              4.0                      9                     8	
</pre>

### Java JDK 별 Gradle 지원 버전
reference : https://docs.gradle.org/current/userguide/compatibility.html#java  
<pre>
11	5.0
17	7.3
19      7.6
23      8.10
</pre>

### 로컬, 개발, 운영 환경에 맞게 프로파일 분리

1. 개발환경
- local(로컬 개발환경) : 각 개발자 PC에서 개발 및 테스트 환경 설정
- dev(서버 개발환경) : 개발자들이 만든 코드를 통합하여 테스트 할 수 있는 서버 환경
- production(운영 환경) : 실제 서비스를 운영하는 환경

2. 개발환경에 맞게 프로퍼티 파일 준비
application.properties
application-local.properties
application-dev.properties
application-prod.properties

3. 적용할 프로퍼티 지정
- application.properties 파일에 활성 프로파일을 dev로 지정
```
spring.profiles.active=dev
```

- 실행할 때 active 프로퍼티 지정
```java
java -jar  XXX.jar                              // application.properties 적용됨
java -jar -Dspring.profiles.active=dev XXX.jar  // application-dev.properties 적용됨
```
- `.gitignore에 *.properties 추가`


### 외부 경로의 리소스(업로드폴더) 접근
```java
import java.util.concurrent.TimeUnit;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfiguration implements WebMvcConfigurer {

	@Value("${file.uploadpath}")
	String uploadpath;
    
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	
      registry.addResourceHandler("/img/**")
              .addResourceLocations("file://" + uploadpath + "/")      
              // 접근 파일 캐싱 시간 
			        .setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES));
    }
}
```


### rombok 업데이트 에러

에러메시지  
<img src="./images/springboot01.png" style="width:250px">  
Unable to collect dependencies for plugin
org.projectlombok:lombok:jar: was not found in https://repo.maven.apache.org/maven2 during a previous attempt. This failure was cached in the local repository and resolution is not reattempted until the update interval of central has elapsed or updates are forced

조치방법  
build > plugin > annotationProcessorPaths > path에 lombok 버전을 지정함

```xml
<lombok.version>1.18.36</lombok.version>
<version>${lombok.version}</version>
```

```xml
	<properties>
		<java.version>17</java.version>
		<lombok.version>1.18.36</lombok.version>
	</properties>
  	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<annotationProcessorPaths>
						<path>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
							<version>${lombok.version}</version>
						</path>
					</annotationProcessorPaths>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<excludes>
						<exclude>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
							<version>${lombok.version}</version>
						</exclude>
					</excludes>
				</confi
				guration>
			</plugin>
		</plugins>
	</build>
```

### properties
1. JAVA를 통해 가져오기  

```java
	@Value("${upload.path}")
	String path;
```

2. html에서 직접 접근하기
```html
<span th:text="${@environment.getProperty('spring.profiles.active')}"></span>
```
