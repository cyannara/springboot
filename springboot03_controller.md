### Controller 작성

1. 컨트롤러 애노테이션
2. 서비스 객체 인젝션
3. 핸들러 메서드와 @XxxMapping
4. 요청 파라미터 가져오기
5. 서비스 호출
6. 뷰에 데이터 전달하기

```java
@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

	private BoardService service;

	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		model.addAttribute("list", service.getList(cri));
    return "board/list";
	}

```

SampleController

```java
@Controller
@RequestMapping("/sample/*")
@Log4j2
public class SampleController {

	@RequestMapping("")
	public String basic() {
		return "sample";
	}
}
```

templates/sample.html

```html
<body>
  sample
</body>
```

application.properties

```
logging.level.org.springframework=debug
```

Console log 확인

```
web.servlet.DispatcherServlet  : GET "/sample/aaa", parameters={}
RequestMappingHandlerMapping   : Mapped to com.example.demo.SampleController#basic()
ContentNegotiatingViewResolver : Selected 'text/html'
```
