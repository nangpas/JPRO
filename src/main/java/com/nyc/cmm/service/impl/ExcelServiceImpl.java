package com.nyc.cmm.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nyc.cmm.service.ExcelService;
import com.nyc.cmm.util.ExcelRead;
import com.nyc.cmm.util.ExcelReadOption;


@Service("excelService")
public class ExcelServiceImpl implements ExcelService{
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);

	@Override
	public void excelUpload(File file) throws Exception {
		ExcelReadOption excelReadOption = new ExcelReadOption();
        excelReadOption.setFilePath(file.getAbsolutePath());
        excelReadOption.setHeaderRow(1);
        excelReadOption.setOutputColumns("A","B","C");
        excelReadOption.setStartRow(2);
        
        
        List<Map<String, String>>excelContent =ExcelRead.read(excelReadOption);
        logger.debug(excelContent.toString());
        for(Map<String, String> article: excelContent){
        	//DB값 insert 실행
        	logger.debug(article.get("아이디"));
        	logger.debug(article.get("이름"));
        	logger.debug(article.get("전화번호"));
        }
	}

}
