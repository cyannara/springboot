package com.example.www.emp.web;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Component
public class PdfView<T> extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String reportFile = (String) model.get("filename");
		HashMap<String, Object> map = (HashMap<String, Object>) model.get("param");
		List<T> result = (List<T>) model.get("data");
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(result);
		
		InputStream jasperStream = getClass().getResourceAsStream(reportFile);
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, beanCollectionDataSource);
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());


	}
}
