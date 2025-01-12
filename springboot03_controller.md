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
