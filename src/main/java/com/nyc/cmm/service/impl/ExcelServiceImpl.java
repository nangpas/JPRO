package com.nyc.cmm.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
                
                if(map.get(column).contains(word)) {
                	result.add(map);
                }
            }
            
        }
        
        return result;

        
	}

}
