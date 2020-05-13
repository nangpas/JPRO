package com.nyc.cmm.service;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ExcelService {
	 List<Map<String, String>> excelUpload(File destFile, String word, String column)  throws Exception;
}
	