### reference

https://mageddo.com/tools/yaml-converter  
https://adjh54.tistory.com/561  
https://sjh9708.tistory.com/169  
https://swagger.io/solutions/api-design/

```yaml
dependencies {
  #swagger
	implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2'
}
```

```xml
	    <dependency>
	        <groupId>org.springdoc</groupId>
	        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
	        <version>2.8.4</version>
	    </dependency>
```

```java

@Tag(name = "Ticket API", description = "티켓 관리 기능 제공")
@RequestMapping("/api")
@Log4j2
@RestController
public class Rest1Controller {

	@Tag(name= "Ticket API")
	@Operation(summary = "티켓조회", description = "티켓정보를 조회합니다.",         responses = {
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
        })
	@GetMapping("/ticket")
	public Ticket convert1(@RequestBody Ticket ticket) {

		log.info("ticket: "+ticket);
		return ticket;

	}

	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {

		log.info("ticket: "+ticket);
		return ticket;

	}

	@PostMapping("/reserve")
	public Reserve comp(@RequestBody Reserve reserve) {

		log.info("owner: " + reserve.getList().get(0).getOwner());
		return reserve;
	}


	@PostMapping("/compMap")
	public JsonNode compMap(@RequestBody JsonNode node) {

		//첫번째 티켓의 owner 출력
		log.info("owner:" + node.get("list").get(0).get("owner").asText() );
		return node;
	}

}
```

```java
@Schema(description = "티켓 정보 DTO")
@Data
public class Ticket {

	@Schema(description = "티켓 고유 ID", example = "12345")
	int tno;

	@Schema(description = "소유자", example = "user12")
	String owner;

	String grade;

	String seatNo;
}

```
