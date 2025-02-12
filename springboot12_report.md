## jasperReport 연동

### 라이브러리 의존성
```xml
		<dependency>
		 <groupId>net.sf.jasperreports</groupId>
		 <artifactId>jasperreports</artifactId>
		 <version>6.9.0</version>
		</dependency>
```

### spring
```java
     	// jasper에 띄울 데이터 date에 맞춰 받아오기
			//List<BoardStatus> result = boardService.getStatus(date);
			
         // 받아온 데이터를 jasper datasource로 등록
			//JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(result);

         
         // return 방식1. 컴파일된 pdf파일을 현재 폴더에 생성
//			JasperExportManager.exportReportToPdfFile(report, "BoardStatus.pdf");
//			return "generated";
```
### TextFiled
- 날짜 포맷 변경  
<img src="./images/report_dateformat.png">


### reference
- ★ https://a-curious.tistory.com/97  
- https://www.javatips.net/api/net.sf.jasperreports.engine.jrexporter  
- https://velog.io/@onerain130/2.-Jasper-Report-Spring-boot-연동-구현-코드  
- https://galid1.tistory.com/565               파일다운로드  
- https://community.jaspersoft.com/forums/topic/68701-update-jrpdfexporter-jrexporterparameter-to-jasperreports-library-700/  

