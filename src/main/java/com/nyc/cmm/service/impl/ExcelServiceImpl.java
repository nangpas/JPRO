package com.nyc.cmm.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nyc.cmm.service.ExcelService;
import com.nyc.cmm.util.ExcelCellRef;
import com.nyc.cmm.util.ExcelFileType;
import com.nyc.cmm.util.ExcelReadOption;


@Service("excelService")
public class ExcelServiceImpl implements ExcelService{
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);

	@Override
	public  List<Map<String, String>> excelUpload(File file, String word, String column) throws Exception {
		ExcelReadOption excelReadOption = new ExcelReadOption();
        excelReadOption.setFilePath(file.getAbsolutePath());
        
        Workbook wb = ExcelFileType.getWorkbook(excelReadOption.getFilePath());
        
        Sheet sheet = wb.getSheetAt(0);
        
        int rowNum = sheet.getPhysicalNumberOfRows();
        int cellNum = 0;
        
        Row row = null;
        Cell cell = null;
        
        String cellName = "";
        
        Map<String, String> map = null;
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        
        
        for(int rowIndex = excelReadOption.getStartRow() - 1; rowIndex < rowNum; rowIndex++) {
            row = sheet.getRow(rowIndex);
            
            if(row != null) {
            	cellNum = row.getPhysicalNumberOfCells();
            	
                map = new HashMap<String, String>();
                
                for(int cellIndex = 0; cellIndex < cellNum; cellIndex++) {
                    
                    cell = row.getCell(cellIndex);
                    cellName = ExcelCellRef.getName(cell, cellIndex);
                    
                    map.put(cellName, ExcelCellRef.getValue(cell));
                }
                
                if(map.get(column).contains(word) || rowIndex == 0) {
                	result.add(map);
                }
            }
            
        }
        
        return result;
	}

	@Override
	public void excelDown(HttpServletResponse response, List<Map<String, String>> list, String fileName)
			throws Exception {
		
		FileOutputStream fout = null;
		String path = "C:\\JPRO\\file\\";
		
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet worksheet = workbook.createSheet();
		XSSFRow row = null;
        
        for(int i = 0; i < list.size(); i++) {
        	Map temp = list.get(i);
        	
        	Collection v = temp.values();
        	Iterator itrv = v.iterator();
        	int cnt = 0;
        	
        	row = worksheet.createRow(i);
       		
        	while(itrv.hasNext()){
       			row.createCell(cnt).setCellValue((String)itrv.next());
       			cnt++;
        	}
        }
        
        File file = new File(path + fileName + "_End.xlsx");
        fout = new FileOutputStream(file);
        workbook.write(fout);
        
        byte fileByte[] = FileUtils.readFileToByteArray(new File(path + fileName + "_End.xlsx"));
        
        response.setContentType("application/octet-stream"); 
		response.setContentLength(fileByte.length); 
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName + "_End.xlsx","UTF-8")+"\";"); 
		response.setHeader("Content-Transfer-Encoding", "binary"); 
		response.getOutputStream().write(fileByte); 
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
}
