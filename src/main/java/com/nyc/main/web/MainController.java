package com.nyc.main.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nyc.cmm.service.MintService;
import com.nyc.main.service.UserVO;

@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Resource(name="defaultService")
	private MintService mintService;
	
	@Inject
    PasswordEncoder passwordEncoder;
	
	/**
     * 로그인 페이지 호출
     * @return string
     */
	@RequestMapping(value = "/login/userLogin.do")
	public String userLogin(HttpServletRequest request, ModelMap model) {
		if (request.getSession().getAttribute("UserVO") != null) { // 로그인이 되어 있으면 바로 main.do로 이동
			UserVO userVO = (UserVO)request.getSession().getAttribute("UserVO");
	        return "redirect:/jpro/main.do";
	    }
	    else {
	    	return "login/userLogin";
	    }
	}
	
	/**
     * 로그인 처리
     * @param vo        - UserVO
     * @param request   -
     * @return result   - 로그인결과(세션정보)
     * @exception Exception
     */
	@ResponseBody
    @RequestMapping(value = "/login/actionLogin.do")
    public Map<String,String> actionLogin(HttpSession session, HttpServletRequest request, @RequestParam Map<String, Object> commandMap, ModelMap model){
        String inputID = (String)commandMap.get("inputID");
        String inputPW = (String)commandMap.get("inputPW");
        
        Map<String, String> result = new HashMap<String,String>();
        result.put("errCd", "1");
        result.put("errMsg", "에러메시지");
        
        try {
            if(inputID == null || inputID.equals("")) {
            	result.put("errCd", "-1");
            	result.put("errMsg", "아이디가 입력되지 않았습니다.");
            }else if(inputPW == null || inputPW.equals("")){
            	result.put("errCd", "-1");
            	result.put("errMsg", "비밀번호가 입력되지 않았습니다.");
            }else{
                Map<String, Object> usrInfo = mintService.detail_map("UserDAO.chckUserLogin", commandMap);

            	if(usrInfo == null || usrInfo.size() == 0) {
            		result.put("errCd", "-1");
                	result.put("errMsg", "일치하는 아이디가 없습니다.");
            	}else {
            		if(passwordEncoder.matches(inputPW, (String)usrInfo.get("password"))) {
            			UserVO userVO = new UserVO();
            			userVO.setUSR_ID((String)usrInfo.get("usrId"));
            			userVO.setUSR_NM((String)usrInfo.get("usrNm"));
            			userVO.setROLE_ID((String)usrInfo.get("roleId"));
                		request.getSession().setAttribute("UserVO", userVO);
                		request.getSession().setMaxInactiveInterval(60*30); // 기본세션시간 : 30분

                		result.put("errCd", "0");
                    	result.put("errMsg", "로그인 성공!");
            		}else {
            			result.put("errCd", "-3");
                    	result.put("errMsg", "아이디 또는 비밀번호가 일치하지 않습니다.");
            		}
            	}
            }
        } catch (Exception e) {
        	result.put("errCd", "-3");
        	result.put("errMsg", e.getMessage());
            logger.debug(e.getMessage());
        }
        
        return result;
	}
}
