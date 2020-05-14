package com.nyc.cmm.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface ExcelService {
	 List<Map<String, String>> excelUpload(File destFile, String word, String column)  throws Exception;
	 void excelDown(HttpServletResponse response, List<Map<String, String>> list, String fileName)  throws Exception;
}
	