## 자바웹기술 변천과정

- Servlet → JSP → Spring MVC → Spring Boot 로 발전
- 현재는 Spring Boot + REST API + Microservices 형태가 대세이며, 점점 더 클라우드와 컨테이너 환경에 적합한 방식으로 발전하고 있습니다.
- 최신 기술(Spring Security, JPA, React/Vue 연동) 적극 활용!

| 시대        | 기술                | 특징                                                |
| :---------- | :------------------ | :-------------------------------------------------- |
| 1995        | JavaScript          | 웹 페이지에 동적인 기능 추가                        |
| 1997        | Servlet             | Java 기반 웹 개발의 시작, HTML 직접 출력            |
| 1999        | JSP                 | HTML과 Java 코드 결합 가능, 유지보수 어려움         |
| 2000년대 초 | Servlet + JSP + MVC | 역할 분리, 유지보수 용이                            |
| 2000년대 초 | AJAX                | 비동기통신                                          |
| 2003        | Spring              | IoC, DI, AOP 도입, 강력한 웹 애플리케이션 개발 가능 |
| 2014        | Spring Boot         | 설정 자동화, 내장 WAS 지원, Microservices 친화적    |
|             |                     |                                                     |

### Servlet (서블릿)

- Java 기반의 웹 애플리케이션 개발을 위한 최초의 기술.
- 웹 서버(톰캣, JBoss 등)에서 실행되며, HTTP 요청과 응답을 처리.
- HTML을 문자열(String)로 작성해야 하는 불편함.
- 비즈니스 로직과 UI 코드가 혼재되어 유지보수 어려움.

```java
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body><h1>Hello Servlet!</h1></body></html>");
    }
}
```

### JSP (Java Server Pages)

- HTML 코드 안에 Java 코드를 포함할 수 있는 기술.
- 서블릿보다 직관적인 웹 개발 가능.
- JSP 내부적으로 서블릿으로 변환되어 실행됨.
- scriptlet (<% %>)을 사용하여 Java 코드 작성 가능.

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <h1>Hello JSP!</h1>
    <%
        String name = "JSP World";
        out.println("<p>Welcome to " + name + "!</p>");
    %>
</body>
</html>
```

### JSP + Servlet

- Servlet + JSP 조합을 통해 MVC(Model-View-Controller) 패턴 사용.
- Controller (Servlet) → Model (Java Beans, DAO) → View (JSP)
- 비즈니스 로직과 UI를 분리하여 유지보수성 개선.

🔹 MVC 구조

Servlet (Controller): 요청을 받아서 Model을 처리하고, JSP로 데이터를 전달.
Java Bean / DAO (Model): DB와 연결하여 데이터를 처리.
JSP (View): 데이터를 받아 사용자에게 표시.

✅ MVC 패턴 장점

역할 분리가 되어 유지보수가 쉬워짐.
JSP에서 비즈니스 로직을 최소화할 수 있음.

### Spring Framework

- 트랜잭션 처리, AOP(관점 지향 프로그래밍), 보안 등의 기능이 부족함.
- Spring은 DI (Dependency Injection), AOP, IoC (Inversion of Control) 컨테이너를 도입하여 유지보수성을 극대화하고 개발 생산성을 높임.
- XML 설정 없이 Annotation 기반 개발 (@Controller, @Service, @Repository)
- IoC 컨테이너를 통한 객체 관리 → 의존성 주입(DI)
- AOP 지원 → 트랜잭션 관리, 로깅 등 편리

```java
@Controller
public class HelloController {
    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("message", "Hello Spring!");
        return "hello"; // hello.jsp (View)
    }
}
```

### Spring Boot

- Spring의 복잡한 설정을 자동화한 프레임워크. @SpringBootApplication 하나로 기본 설정 자동화.
- 내장 Tomcat 제공 → 배포가 용이. jar 파일 하나로 실행 가능.
- XML 설정 없이 application.properties만으로 환경 설정 가능.
- Microservices 아키텍처와 클라우드 환경에 최적화됨

## 웹 개발 변천사

| 시대        | 주요 특징                   | 대표 기술                    |
| :---------- | :-------------------------- | :--------------------------- |
| 1990년대 초 | 정적 웹 (HTML, CSS)         | HTML, CSS                    |
| 1995~2005년 | 동적 웹 & 데이터베이스      | PHP, ASP, JSP, MySQL         |
| 2005~2010년 | AJAX & 대화형 웹            | AJAX, JSON, jQuery           |
| 2010~2020년 | SPA & JavaScript 프레임워크 | React.js, Vue.js, Angular.js |
| 2020년 이후 | 클라우드 & AI 기반 웹       | Serverless, GraphQL, AI      |

## 개발환경구축

https://docs.spring.io/spring-boot/installing.html

https://scoop.sh/

```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
Invoke-RestMethod -Uri https://get.scoop.sh | Invoke-Expression
```

error

```
Multiple types found with build 'gradle' and format 'project'
use --type with a more specific value
[gradle-project, gradle-project-kotlin]
```

```powershell
Get-ChildItem -Recurse -File
```
