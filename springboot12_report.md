## 리포팅 툴
- 데이터 소스와 연결하여 조회된 데이터를 기반으로 차트와 테이블 형태로 제공하고 PDF 출력
- 다양한 플랫폼과 분선되 데이터를 통합적으로 분석, 가공해 경영자에게 리포팅 할 수 있는 솔루션

### 리포팅 툴 종류
-  FineReport : 엑셀과 유사한 인터페이스
-  JasperReport : 커뮤니티 버전과 상업용 버전
-  SAP Crystal Report : PDF, 스프레드시트(spreadsheet) 및 HTML 등의 양식으로 배포
-  Zoho Analytics : 차트, 피벗테이블(pivot table), KPI 위젯 및 사용자 지정 테마 대시보드형식의 다양한 시각화 옵션을 제공
-  OZ Report : 오즈리포트는 지난 23년동안 한국 리포팅&전자문서 시장을 선도하며 한국 시장 점유율 1위를 차지하고 있는 셀프 리포팅 솔루션입니다

## jasperReport 연동

### 라이브러리 의존성
```xml
<dependency>
    <groupId>net.sf.jasperreports</groupId>
    <artifactId>jasperreports</artifactId>
    <version>6.6.0</version>
    <scope>compile</scope>
</dependency>
```

### spring
```java
	// pdf 출력
	@RequestMapping("emp/pdf")
	public void report(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection conn = datasource.getConnection();
		
		Resource reportResource  = new ClassPathResource("reports/empInfo.jrxml");

		// 2. JRXML 컴파일
        JasperReport jasperReport = JasperCompileManager.compileReport(reportResource.getInputStream());
        
        // 3. PDF 생성
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
		
		response.setContentType("application/pdf");
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}
```

### ViewResolver 이용
```java

import java.util.Collection;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.AbstractView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class JasperPdfView extends AbstractView {

    private Resource reportResource;      // JRXML 위치
     
    public JasperPdfView(String path) {
    	this.reportResource = new ClassPathResource(path);
    	setContentType("application/pdf");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 1. 데이터 소스 생성
        Collection<?> items = (Collection<?>) model.get("datas");
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(items);

        // 2. JRXML 컴파일
        JasperReport jasperReport = JasperCompileManager.compileReport(reportResource.getInputStream());

        // 3. PDF 생성
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, model, dataSource);

        // 4. PDF 브라우저 출력
        response.setContentType(getContentType());
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}
```
```java
	@GetMapping("report2")
	public ModelAndView report2() {
		// view 지정
		ModelAndView mv = new ModelAndView(new JasperPdfView("reports/emp.jrxml"));
		
		// 파라미터
		mv.addObject("P_EMPNAME", "hong gildong");
		
		// 데이터 조회
		EmpVO vo = new EmpVO();
		vo.setFirst(1);
		vo.setLast(10);
		mv.addObject("datas", empService.selectEmp(vo));
		
		return mv;
	}
```

### content type 지정
```java
	// view 지정
	JasperPdfView view = new JasperPdfView("reports/emp.jrxml");
	view.setContentType("application/octet-stream");
	//view.setContentType("application/pdf");
	ModelAndView mv = new ModelAndView(view);
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

