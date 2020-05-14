package com.nyc.shop.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.jfree.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
     * 스마트에디터 테스트 페이지
     * @return string
     */
	@RequestMapping(value = "/jpro/coin.do")
	public String coin(HttpServletRequest request, ModelMap model) {
		return "jpro/coin";
	}
	
	/**
     * 엑셀 테스트 페이지
     * @return string
     */
	@RequestMapping(value = "/jpro/excelTest1.do")
	public String excelTest1(HttpServletRequest request, ModelMap model) {
		return "jpro/excelTest";
	}
	
	/**
     * 엑셀 업로드 테스트 페이지
     * @return string
	 * @throws Exception 
     */
	@ResponseBody
	@RequestMapping(value = "/jpro/extExcelFile.do")
	public int excelTest(MultipartHttpServletRequest multiRequest, @RequestParam Map<String, Object> commandMap, HttpServletResponse response) {
		String word = (String)commandMap.get("word");
		String column = (String)commandMap.get("column");
		String fileName = "";
		int result = 0;
		
		MultipartFile excelFile = multiRequest.getFile("excelFile");
        
        if(excelFile==null || excelFile.isEmpty()){
            result = 1;
            logger.debug("엑셀파일이 아닙니다.");
        }
        
        //String root_path = multiRequest.getSession().getServletContext().getRealPath("/");
        //String attach_path = "resources\\upload\\";
        //logger.debug(root_path + attach_path + excelFile.getOriginalFilename());
        //File file = new File(root_path + attach_path + excelFile.getOriginalFilename());
        
        String path = "C:\\JPRO\\file\\";
        
        fileName = excelFile.getOriginalFilename();
        
        int pos = fileName .lastIndexOf(".");
        fileName = fileName.substring(0, pos);
        
        File file = new File( path + excelFile.getOriginalFilename());
        
        	
        try{
        	word = new String(word.getBytes("8859_1"),"utf-8");
            excelFile.transferTo(file);
            List<Map<String, String>> list = excelService.excelUpload(file, word, column);
            excelService.excelDown(response, list, fileName);
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
	
	/**45
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
	@ResponseBody
	@RequestMapping(value = "/jpro/testCrawling2.do")
	public List<Map<String, String>> testCrawling2(HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> commandMap){
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		String url = "https://www.bithumb.com/";
		
		
		
		WebDriver driver = null;
		
		try {
			System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
			driver = new ChromeDriver();
			
			driver.get(url);
			//
			List<WebElement> element = null;
			element = driver.findElements(By.className("fvtWrap"));
			for(int i = 0; i < element.size(); i++) {
				//코인 영어명
				String coinNameEng = element.get(i).getAttribute("data-coin");
				
				//코가격 찾기
				WebElement tempElement = element.get(i).findElement(By.id("assetReal" + coinNameEng + "_KRW"));
				Double coinPrice = Double.valueOf(tempElement.getAttribute("data-sorting").replace(",", ""));
				
				//코인한글명
				WebElement tempElement2 = element.get(i).findElement(By.className("sort_coin"));
				String coinNameKr = tempElement2.getAttribute("data-sorting");
				
				//전일 대비 비교
				WebElement tempElement3 = element.get(i).findElement(By.id("assetRealRate" + coinNameEng + "_KRW"));
				String coinRate = tempElement3.getText();
				
				//거래금액
				WebElement tempElement4 = element.get(i).findElement(By.id("assetReal" + coinNameEng + "_KRW2KRW"));
				String coinTranPrice = tempElement4.getText();
				
				//시가총액
				WebElement tempElement5 = element.get(i).findElement(By.className("sort_total"));
				String coinTotal = tempElement5.getText();
				
				
				Map<String, String> coinMap = new HashMap<String, String>();
				
				coinMap.put("coinName", coinNameKr);
				coinMap.put("coinPrice", coinPrice + "");
				coinMap.put("coinRate", coinRate + "");
				coinMap.put("coinTranPrice", coinTranPrice + "");
				coinMap.put("coinTotal", coinTotal + "");
				
				
				result.add(coinMap);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		
		driver.quit();
		
		return result;
	}
	
	
	/**45
     * 테스트 페이지
     * @return string
	 * @throws org.json.simple.parser.ParseException 
     */
	@ResponseBody
	@RequestMapping(value = "/jpro/testCrawling3.do")
	public List<Map<String, String>> testCrawling3(HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> commandMap){
		
		// can only grab first 100 results
		String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
		String url = "https://www.bithumb.com/";
		List<Map<String,String>> result = new ArrayList<Map<String, String>>();
		
		try {
		    Document doc = Jsoup.connect(url).userAgent(userAgent).referrer("https://www.bithumb.com/").get();
		    Elements elements = doc.select("tr.fvtWrap");
		    
		    for (Element element : elements) {
		    	Map<String, String> coinMap = new HashMap<String, String>();
		    	
		    	//코인 영어 이름
		    	String coinName = element.attr("data-coin");

		    	
		    	//코인 한글명
		    	Elements tmpEl0 = element.getElementsByClass("sort_coin");
		    	String coinNameKr = tmpEl0.get(0).attr("data-sorting");
		    	
		    	//코인 가격
		    	Element tmpEl1 = element.getElementById("assetReal" + coinName + "_KRW");
		    	String coinPrice = tmpEl1.attr("data-sorting").replace(",", "");
		    	
		    	//전일 대비 비교
		    	Element tmpEl2 = element.getElementById("assetRealRate" + coinName + "_KRW");
		    	String coinRate = tmpEl2.text();
		    	
		    	//거래금액
		    	Element tmpEl3 = element.getElementById("assetReal" + coinName + "_KRW2KRW");
		    	String coinTranPrice = tmpEl3.text();
		    	
		    	//사기총액
		    	Elements tmpEl4 = element.getElementsByClass("sort_total");
		    	String coinTotal = tmpEl4.get(0).text();
		    	
		    	
		    	coinMap.put("coinName", coinName);
		    	coinMap.put("coinNameKr", coinNameKr);
		    	coinMap.put("coinPrice", coinPrice);
		    	coinMap.put("coinRate", coinRate);
		    	coinMap.put("coinTranPrice", coinTranPrice);
		    	coinMap.put("coinTotal", coinTotal);
		    	
		    	result.add(coinMap);
		    }
		} catch (IOException ie) {
			logger.debug(ie.getMessage());
		} catch(Exception e){
			logger.debug(e.getMessage());
		}
		
		return result;
	}
	
	/**45
     * 테스트 페이지
     * @return string
	 * @throws org.json.simple.parser.ParseException 
     */
	@ResponseBody
	@RequestMapping(value = "/jpro/testCrawling4.do")
	public List<Map<String, String>> testCrawling4(HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> commandMap){
		
		// can only grab first 100 results
		String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
		String url = "https://www.bithumb.com/";
		List<Map<String,String>> result = new ArrayList<Map<String, String>>();
		
		try {
		    Document doc = Jsoup.connect(url).userAgent(userAgent).referrer("https://www.bithumb.com/").get();
		    Elements elements = doc.select("tr.fvtWrap");
		    
		    for (Element element : elements) {
		    	Map<String, String> coinMap = new HashMap<String, String>();
		    	
		    	//코인 영어 이름
		    	String coinName = element.attr("data-coin");

		    	
		    	//코인 한글명
		    	Elements tmpEl0 = element.getElementsByClass("sort_coin");
		    	String coinNameKr = tmpEl0.get(0).attr("data-sorting");
		    	
		    	//코인 가격
		    	Element tmpEl1 = element.getElementById("assetReal" + coinName + "_KRW");
		    	String coinPrice = tmpEl1.attr("data-sorting").replace(",", "");
		    	
		    	//전일 대비 비교
		    	Element tmpEl2 = element.getElementById("assetRealRate" + coinName + "_KRW");
		    	String coinRate = tmpEl2.text();
		    	
		    	//거래금액
		    	Element tmpEl3 = element.getElementById("assetReal" + coinName + "_KRW2KRW");
		    	String coinTranPrice = tmpEl3.text();
		    	
		    	//사기총액
		    	Elements tmpEl4 = element.getElementsByClass("sort_total");
		    	String coinTotal = tmpEl4.get(0).text();
		    	
		    	
		    	coinMap.put("coinName", coinName);
		    	coinMap.put("coinNameKr", coinNameKr);
		    	coinMap.put("coinPrice", coinPrice);
		    	coinMap.put("coinRate", coinRate);
		    	coinMap.put("coinTranPrice", coinTranPrice);
		    	coinMap.put("coinTotal", coinTotal);
		    	
		    	result.add(coinMap);
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
	public String test(HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> commandMap) {
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
	
	/**
     * 텍스트 붙이기
     * @return string
	 * @throws org.json.simple.parser.ParseException 
     */
	@RequestMapping(value = "/jpro/sumText.do")
	public String sumText(HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletResponse response) {
		return "jpro/sumText";
	}
	
	
	/**
     * 텍스트 붙이기
     * @return string
	 * @throws org.json.simple.parser.ParseException 
     */
	@RequestMapping(value = "/jpro/sumTextFile.do")
	public void sumTextFile(MultipartHttpServletRequest multiRequest, ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletResponse response) {
		
		Iterator<String> iterator = multiRequest.getFileNames();
		MultipartFile multipartFile = null;
		String filePath = "/bnm2027/file/";
		String str = "";
		String temp = "";
		String fileFullName = "";
		String fileName = "";
		
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMdd HHmmss");
		Date timeD = new Date();
		String time = format1.format(timeD);
		
		try {
			while (iterator.hasNext()) {
				multipartFile = multiRequest.getFile(iterator.next());
				fileName = multipartFile.getOriginalFilename();
				int pos = fileName.lastIndexOf(".");
				fileFullName = fileFullName + fileName.substring(0, pos) + "_";
				
				
				
				InputStreamReader isr = new InputStreamReader(multipartFile.getInputStream());
				BufferedReader buffer = new BufferedReader(isr);
				while((temp = buffer.readLine() ) != null){
					str += temp + "\n";
				}
			}
			
            File file = new File(filePath + fileFullName + time + ".txt") ;
            FileWriter fw = new FileWriter(file, true) ;
            fw.write(str);
            fw.flush();
            fw.close();
            
			byte fileByte[] = FileUtils.readFileToByteArray(new File(filePath + fileFullName + time + ".txt"));
			
			response.setContentType("application/octet-stream"); 
			response.setContentLength(fileByte.length); 
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileFullName + time + ".txt","UTF-8")+"\";"); 
			response.setHeader("Content-Transfer-Encoding", "binary"); 
			response.getOutputStream().write(fileByte); 
			response.getOutputStream().flush();
			response.getOutputStream().close();
            
			
			
		} catch (Exception e) {
			Log.debug(e.getMessage());
		}
		
	}
	
}