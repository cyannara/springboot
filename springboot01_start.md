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
				</configuration>
			</plugin>
		</plugins>
	</build>
```
