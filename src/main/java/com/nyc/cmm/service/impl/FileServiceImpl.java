package com.nyc.cmm.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nyc.cmm.service.FileService;

@Service("fileService")
public class FileServiceImpl implements FileService{
	
	private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	
	public static final int BUFF_SIZE = 2048;
	
	@Override
	public Map<String,String> save_file(MultipartHttpServletRequest multiRequest, Map<String, Object> commandMap) throws Exception {
		Map<String,String> result = new HashMap<String, String>(); //반환값
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)multiRequest;	// 파일정보가 담기는 변수
		Iterator<String> fileIter = mptRequest.getFileNames();
		
		SimpleDateFormat yearFormat = new SimpleDateFormat ( "yyyy");	// 날짜 포맷 지정 ex)20191224
		SimpleDateFormat monthFormat = new SimpleDateFormat ( "MM");	// 날짜 포맷 지정 ex)20191224
		SimpleDateFormat dayFormat = new SimpleDateFormat ( "dd");	// 날짜 포맷 지정 ex)20191224
    	Calendar time = Calendar.getInstance();	// 현재날짜를 추출하기 위한 Calendar 객체 선언
		
    	String fileExt = "";
    	String filePath = "";
    	String fileName = "";
		try {
        	while (fileIter.hasNext()) {
        		
        		MultipartFile mFile = mptRequest.getFile((String)fileIter.next());
        		
        		// 클라이언트가 요청한 파일이 존재하는 경우
        		if (mFile.getSize() > 0) {
    				fileExt = mFile.getOriginalFilename().substring(mFile.getOriginalFilename().lastIndexOf('.')).toLowerCase(); // 확장자
    				filePath = "";
    				filePath += "/" + yearFormat.format(time.getTime());
    				filePath += "/" + monthFormat.format(time.getTime());
    				filePath += "/" + dayFormat.format(time.getTime());
    				
    				fileName = getCurrentTime("YYYYMMDDHHmmssSSS") + fileExt;
    				writeFile(mFile, fileName, filePath);
        		}
        	}
        	
        	result.put("0", filePath + "/" + fileName);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			result.put("1", "저장중 오류 발생");
		}
		
		return result;
	}
	
	
	/**
	 * 파일을 실제 물리적인 경로에 생성한다. 
	 *
	 * @param file
	 * @param newName
	 * @param stordFilePath
	 * @throws Exception
	 */
	protected static void writeFile(MultipartFile file, String newName, String stordFilePath) throws Exception {
		InputStream stream = null;
		OutputStream bos = null;

		try {
			stream = file.getInputStream();
			File cFile = new File(stordFilePath);

			if (!cFile.isDirectory()){
				if (cFile.mkdirs()){
					logger.debug("[file.mkdirs] saveFolder : Creation Success ");
					
					/*
					if(EgovProperties.getProperty("Globals.OsType").equals("UNIX")) {
						Runtime.getRuntime().exec("chown -R msitweb:msit " + stordFilePath);
						Runtime.getRuntime().exec("chmod -R 775 " + stordFilePath);
						LOGGER.debug("명렁어 실행됨 : chown -R msitweb:msit " + stordFilePath);
					}*/
				}else{
					logger.error("[file.mkdirs] saveFolder : Creation Fail ");
				}
			}

			bos = new FileOutputStream(stordFilePath + File.separator + newName);

			int bytesRead = 0;
			byte[] buffer = new byte[BUFF_SIZE];

			while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
		} finally {
			bos.close();
			stream.close();
		}
		/*
		if(EgovProperties.getProperty("Globals.OsType").equals("UNIX")) {
			Runtime.getRuntime().exec("chown -R msitweb:msit " + stordFilePath + "/" + newName);
			Runtime.getRuntime().exec("chmod -R 775 " +stordFilePath + "/" + newName);
			LOGGER.debug("명렁어 실행됨 : chown -R msitweb:msit " + stordFilePath + "/" + newName);
		}*/
	}
	
	public static String getCurrentTime(String timeFormat){
        return new SimpleDateFormat(timeFormat).format(System.currentTimeMillis());
    }
	
}
