package com.nyc.shop.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nyc.cmm.service.MintService;

@Controller
public class ShopController {
	private static final Logger logger = LoggerFactory.getLogger(ShopController.class);
	
	@Resource(name="defaultService")
	private MintService mintService;
	
	/**
     * 메인페이지 호출
     * @return string
     */
	@RequestMapping(value = "/jpro/main.do")
	public String home(HttpServletRequest request, ModelMap model) {
		return "jpro/main";
	}
}