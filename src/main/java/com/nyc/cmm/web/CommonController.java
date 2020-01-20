package com.nyc.cmm.web;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nyc.cmm.service.ExcelService;
import com.nyc.cmm.service.FileService;
import com.nyc.cmm.service.MintService;

@Controller
public class CommonController {
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Resource(name="defaultService")
	private MintService mintService;
	
	@Resource(name="excelService")
	private ExcelService excelService;
	
	@Resource(name="fileService")
	private FileService fileService;
	
	/**
     * 메인페이지 호출
     * @return string
	 * @throws Exception 
     */
	@ResponseBody
	@RequestMapping(value = "/uploadFile.do")
	public Map<String,String> uploadFile(MultipartHttpServletRequest multiRequest, @RequestParam Map<String, Object> commandMap) {
		Map<String,String> result = null;
		
		try {
			result = fileService.save_file(multiRequest, commandMap);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return result;
	}
}