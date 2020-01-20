package com.nyc.cmm.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FileService {
	Map<String,String> save_file(MultipartHttpServletRequest multiRequest, Map<String, Object> commandMap) throws Exception;
}
