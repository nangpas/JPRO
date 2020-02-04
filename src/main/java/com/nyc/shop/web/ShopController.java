package com.nyc.shop.web;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
import com.nyc.cmm.service.MintService;

@Controller
public class ShopController {
	private static final Logger logger = LoggerFactory.getLogger(ShopController.class);
	
	@Resource(name="defaultService")
	private MintService mintService;
	
	@Resource(name="excelService")
	private ExcelService excelService;
	
	/**
     * 메인페이지 호출
     * @return string
     */
	@RequestMapping(value = "/jpro/main.do")
	public String main(HttpServletRequest request, ModelMap model) {
		return "jpro/main";
	}
	
	
	/**
     * 메인페이지 호출
     * @return string
     */
	@RequestMapping(value = "/jpro/fileTest.do")
	public String fileTest(HttpServletRequest request, ModelMap model) {
		return "jpro/fileTest";
	}
	
	/**
     * 스마트에디터 테스트 페이지
     * @return string
     */
	@RequestMapping(value = "/jpro/smartEdit.do")
	public String smartEdit(HttpServletRequest request, ModelMap model) {
		return "jpro/smartEdit";
	}
	
	/**
     * 엑셀 업로드 테스트 페이지
     * @return string
	 * @throws Exception 
     */
	@ResponseBody
	@RequestMapping(value = "/jpro/excelTest.do")
	public int excelTest(MultipartHttpServletRequest multiRequest, @RequestParam Map<String, Object> commandMap) {
		int result = 0;
		
		MultipartFile excelFile = multiRequest.getFile("excelFile");
        
        if(excelFile==null || excelFile.isEmpty()){
            result = 1;
            logger.debug("엑셀파일이 아닙니다.");
        }
        
        String root_path = multiRequest.getSession().getServletContext().getRealPath("/");  
        String attach_path = "resources/upload/";
        
        logger.debug(root_path + attach_path + excelFile.getOriginalFilename());
        File file = new File(root_path + attach_path + excelFile.getOriginalFilename());
        	
        try{
            excelFile.transferTo(file);
            excelService.excelUpload(file);
        }catch(IllegalStateException ie){
        	logger.debug(ie.getMessage());
        }catch(IOException ioe) {
        	logger.debug(ioe.getMessage());
        }catch(Exception e) {
        	logger.debug(e.getMessage());
        }finally {
        	if( file.exists() ){
        		if(file.delete()){
        			logger.debug(file.getName() + "파일삭제성공");
        		}else{
        			logger.debug(file.getName() + "파일삭제실패");
        		}
        	}else{
        		logger.debug("파일이 존재하지 않습니다..");
        	}
		}
		
		return result;
	}
	
	/**
     * 테스트 페이지
     * @return string
	 * @throws org.json.simple.parser.ParseException 
     */
	@ResponseBody
	@RequestMapping(value = "/jpro/testCrawling.do")
	public List<String> testCrawling(HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> commandMap){
		
		// can only grab first 100 results
		String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
		String url = "https://www.google.com/search?q=" + commandMap.get("KEYWARD") + "&source=lnms&tbm=isch&sa=X&ved=2ahUKEwiuzIj3tLfnAhWTOnAKHY6RBzMQ_AUoAXoECAwQAw&biw=1920&bih=1057";
		List<String> result = new ArrayList<String>();
		
		try {
		    Document doc = Jsoup.connect(url).userAgent(userAgent).referrer("https://www.google.com/").get();
		    Elements elements = doc.select("img.rg_i");
		    
		    for (Element element : elements) {
		    	result.add(element.attr("data-iurl"));
		    }
		} catch (IOException ie) {
			logger.debug(ie.getMessage());
		} catch(Exception e){
			logger.debug(e.getMessage());
		}
		
		return result;
	}
	
	
	/**
     * 테스트 페이지
     * @return string
	 * @throws org.json.simple.parser.ParseException 
     */
	@RequestMapping(value = "/jpro/test.do")
	public String test(HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> commandMap) throws org.json.simple.parser.ParseException {
		return "jpro/test";
	}
	
	/*
	String url = "https://www.google.co.kr/search?q=%ED%86%B0%EC%BA%A3&hl=ko&sxsrf=ACYBGNRPPVThdcfd1esObhsdZuI9iOydnQ:1580707260039&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjy0um10bTnAhXJy4sBHX-UC9UQ_AUoAXoECBIQAw&biw=1920&bih=1057";
	WebDriver driver = null;
	
	try {
		System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
		//System.setProperty("webdriver.chrome.driver", "/chromedriver");
		driver = new ChromeDriver();
		
		driver.get(url);
		//
		List<WebElement> element = null;
		element = driver.findElements(By.className("rg_i"));
		for(int i = 0; i < element.size(); i++) {
			logger.debug(element.get(i).getAttribute("innerHTML"));
			logger.debug(element.get(i).getAttribute("src"));
		}
		
		model.addAttribute("text", element.get(1).getAttribute("src"));
		
	} catch (Exception e) {
		logger.debug(e.getMessage());
	}finally {
		driver.close();
	}
	*/
}