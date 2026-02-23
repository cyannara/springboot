package com.example.www.emp.web;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class PdfController {

	@Autowired
	DataSource datasource;

	
	@RequestMapping("report")
	public void report( HttpServletResponse response) throws Exception {
		Connection conn = datasource.getConnection();
		// 소스 컴파일 jrxml -> jasper
		InputStream stream = getClass().getResourceAsStream("/reports/emp.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(stream); // 파라미터 맵
		HashMap<String, Object> map = new HashMap<>();
		//map.put("P_DEPARTMENT_ID", dept);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}	
	
	@RequestMapping("reportgroup")
	public void report1(BigDecimal dept, HttpServletResponse response) throws Exception {
		Connection conn = datasource.getConnection();
		InputStream jasperStream = getClass().getResourceAsStream("/reports/deptgroup.jasper");
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream); // 파라미터 맵
		HashMap<String, Object> map = new HashMap<>();
		map.put("P_DEPARTMENT_ID", dept);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}

	@RequestMapping("reportgroup2")
	public void reportgroup(BigDecimal dept, HttpServletResponse response) throws Exception {
		Connection conn = datasource.getConnection();
		// 소스 컴파일 jrxml -> jasper
		InputStream stream = getClass().getResourceAsStream("/reports/deptgroup.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(stream); // 파라미터 맵
		HashMap<String, Object> map = new HashMap<>();
		map.put("P_DEPARTMENT_ID", dept);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}

	
	@RequestMapping("reportsub")
	public void report4(BigDecimal dept, HttpServletResponse response) throws Exception {
		Connection conn = datasource.getConnection(); 
		InputStream jasperStream = getClass().getResourceAsStream("/reports/master.jasper"); 
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream); // 파라미터 맵
		HashMap<String, Object> map = new HashMap<>();
		//map.put("P_DEPARTMENT_ID", dept);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}
	
}
