package com.nyc.admin.web;

import java.util.List;
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

import com.nyc.cmm.service.MintService;
import com.nyc.main.service.UserVO;

@Controller
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	
	@Resource(name="defaultService")
	private MintService mintService;
	
	/**
     * 권한관리 페이지
     * @return string
     */
	@RequestMapping(value = "/admin/role.do")
	public String role(HttpServletRequest request, ModelMap model) {
		//권한체크
		UserVO userVO = (UserVO)request.getSession().getAttribute("UserVO");
		
		if(userVO.getROLE_ID().equals("0000000")) {
			return "admin/role";
		}else {
			return "redirect:/jpro/main.do";
		}
	}
	
	/**
     * 권한 조회
     * @return string
     */
	@ResponseBody
	@RequestMapping(value = "/admin/selectRoleList.do")
	public List<Map<String, Object>> selectRoleList(HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> commandMap) {
		
		List<Map<String, Object>> result = null;
		
		try {
			result = mintService.list_map("AdminDAO.selectRoleList", commandMap);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		
		return result;
	}
	
	/**
     * 권한 추가
     * @return string
     */
	@ResponseBody
	@RequestMapping(value = "/admin/insertRole.do")
	public int insertRole(HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> commandMap) {
		
		int result = 0;
		
		try {
			mintService.insert("AdminDAO.insertRole", commandMap);
			result = 1;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			result = 0;
		}
		
		return result;
	}
	
	/**
     * 권한 삭제
     * @return string
     */
	@ResponseBody
	@RequestMapping(value = "/admin/deleteRole.do")
	public int deleteRole(HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> commandMap) {
		
		int result = 0;
		
		try {
			mintService.update("AdminDAO.deleteRole", commandMap);
			result = 1;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			result = 0;
		}
		
		return result;
	}
	
}