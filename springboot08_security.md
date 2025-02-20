## spring security

### 작업순서

https://docs.spring.io/spring-security/reference/getting-spring-security.html  
https://docs.spring.io/spring-security/reference/features/index.html 

```xml
<dependencies>
	<!-- ... other dependency elements ... -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
	</dependency>
</dependencies>
```
1. spring security starter 추가  
```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<scope>test</scope>
		</dependency>      
		<dependency>
			<groupId>org.thymeleaf.extras</groupId>
			<artifactId>thymeleaf-extras-springsecurity6</artifactId>
		</dependency>  
```

2. security config  

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean 
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/", "/home", "/css/*", "/js/*", "/assets/*").permitAll()
				.requestMatchers("/admin/*").hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.permitAll()
			)
			.logout((logout) -> logout.deleteCookies("JSESSIONID").permitAll())
			//.csrf(csrf -> csrf.disable())
			.csrf((csrf) -> csrf.ignoringRequestMatchers("/api/*")   );
			;

		return http.build();
	}

	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("1111")
				.roles("USER")
				.build();
		UserDetails admin =
				 User.withDefaultPasswordEncoder()
					.username("admin")
					.password("1111")
					.roles("ADMIN")
					.build();

		return new InMemoryUserDetailsManager(user,admin);
	}
}
```

1. 필터  
https://docs.spring.io/spring-security/reference/servlet/architecture.html  

WebSecurityConfigurerAdapter
securityFilterChain

리스너  


2. Cross Site Request Forgery (CSRF)  Protection
https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html  


```html
<form action="/logout" method="post">
  <input type="submit" 	value="Log out" />
  <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"/>
</form>
```

3. ajax 요청에서 token 전달  
```html
<head>
	<meta name="_csrf" content="${_csrf.token}"/>
	<!-- default header name is X-CSRF-TOKEN -->
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
  <script>
		$(function () {
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			$(document).ajaxSend(function(e, xhr, options) {
				xhr.setRequestHeader(header, token);
			});
		});
	</script>
</head>


```

4. toast grid에서 토큰 전달  

### CORS 
https://docs.spring.io/spring-security/reference/servlet/integrations/cors.html  

Users can integrate the CorsFilter with Spring Security by providing a CorsConfigurationSource  

```java
@Bean
UrlBasedCorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
    configuration.setAllowedMethods(Arrays.asList("GET","POST"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}

@Bean
public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
	http
		.securityMatcher("/api/**")
		.cors((cors) -> cors
			.configurationSource(apiConfigurationSource())
		)
}
```
### reference
- https://ddonghyeo.tistory.com/56  