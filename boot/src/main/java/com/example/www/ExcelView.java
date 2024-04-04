package com.example.www;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

public class ExcelView extends AbstractXlsView{

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

    	String filename = (String)model.get("filename");
        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");

        //@SuppressWarnings("unchecked")
        List<Map> datas = (List<Map>) model.get("datas");
        
        // create excel xls sheet
        Sheet sheet = workbook.createSheet("sheet1");
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        style.setFont(font);


        // create header row
        Row header = sheet.createRow(0);
        int i=0;
        for(Object key: datas.get(0).keySet().toArray()) {
        	header.createCell(i).setCellValue((String)key);
        	header.getCell(i++).setCellStyle(style);
        }

        // create data row
        int rowCount = 1;
        for(Map data : datas){
            Row row =  sheet.createRow(rowCount++);
            i=0;
            for(Object key: datas.get(0).keySet().toArray()) {
            	row.createCell(i++).setCellValue(data.get(key)!=null?data.get(key).toString():"");
            }
        }

    }

}