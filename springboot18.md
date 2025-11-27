/*
 공공데이터 API 호출 테스트
*/
public class RestTemplateTest {

	@Test
	public void test() {
  
		RestTemplate restTemplate = new RestTemplate();
	
    String urlString = "https://apis.data.go.kr/1160100/service/GetCorpBasicInfoService_V2/getCorpOutline_V2";
    String serviceKey = "38j7UmVyYljXo%2BQx3A84zbqruCCSTG%2FM7UVe3DLR4R8F39%2ZWpp%2FpCokQ%3D%3D";  //인코딩
		
    URI uri = UriComponentsBuilder.fromUriString(urlString)
		        .queryParam("serviceKey", serviceKey)  
		        .queryParam("pageNo", 1)
		        .queryParam("resultType","json")
		        .queryParam("numOfRows", 10)
		        .build(true)     // 인코딩 여부. 인코딩을 해야하면 false 
		        .toUri();

		Map response = restTemplate.getForObject(uri, Map.class);
		System.out.println(response);
    
		// xml 인 경우 헤더 설정		
		// HttpHeaders headers = new HttpHeaders();
		// headers.set("Accept", "application/json");   // XML 응답을 원함
		// HttpEntity<String> entity = new HttpEntity<>(headers);
		// ResponseEntity<String> response =restTemplate.exchange(uri, HttpMethod.GET, entity, Map.class);
	}
}
