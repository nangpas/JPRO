package com.nyc.cmm.interceptor;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		boolean result = false;
		String webRoot = request.getContextPath();
		
		try {
	         String id = (String) request.getSession().getAttribute("usrId");
	            if(id == null ){
	                if(isAjaxRequest(request)){
	                    response.sendError(400);
	                    return false;
	                }else{
	                    response.sendRedirect(webRoot + "/usrLogin.do");  
	                    result =  false;
	                }
	            }else{
	                result =  true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	            return false;
	        }
	        return result;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}
	
	
	private boolean isAjaxRequest(HttpServletRequest req) {
        String header = req.getHeader("AJAX");
        if ("true".equals(header)){
         return true;
        }else{
         return false;
        }
    }
	
}
