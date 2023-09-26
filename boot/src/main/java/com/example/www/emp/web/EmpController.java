package com.example.www.emp.web;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.www.emp.service.EmpService;
import com.example.www.emp.service.EmpVO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class EmpController {
	
	@Autowired	EmpService empService;

	@RequestMapping("/main") 
	public String main(){
		return "main"; 
	}
	
	@RequestMapping("/empList")
	public String empList(Model model) {
		model.addAttribute("empList", empService.getEmpList(null));
		return "empList";
	}
	
	@GetMapping(value="generate")
	public ResponseEntity<byte[]> generateStatus() throws Exception, JRException {
    
    	// jasper에 띄울 데이터 date에 맞춰 받아오기
		List<EmpVO> result = empService.getEmpList(null);
		
        // 받아온 데이터를 jasper datasource로 등록
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(result);
        
        // jasper 컴파일할 양식 설정 - 만들어둔 jrxml 파일 경로 설정
		JasperReport compileReport = JasperCompileManager.compileReport(this.getClass().getResourceAsStream("/emp2.jrxml"));
		
        // datasource를 매핑해 양식(jrxml)에 맞게 컴파일
        HashMap<String, Object> map =new HashMap<>();
		JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        
        // return 방식1. 컴파일된 pdf파일을 현재 폴더에 생성
//		JasperExportManager.exportReportToPdfFile(report, "BoardStatus.pdf");
//		return "generated";
		
        // return 방식2. 프린트 및 adobe pdf 화면 띄우기
        // *주의: 프론트에서 화면을 띄울 수 없고, 서버 url을 직접 띄워야함..
		byte[] data = JasperExportManager.exportReportToPdf(report);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=Emp.pdf");
		
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
	}
}
