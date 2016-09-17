/*
package com.deals;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class SecurityConfig extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean b = false;
		
		System.out.println("URL ::: "+request.getRequestURI());
		//String uri = request.getRequestURI();
		HttpSession session = request.getSession();
		System.out.println("Session Check Interceptor :::: "+session);
		if(session != null){
			b = true;
		}else{
			
		}
		return b;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
		
	}
	
	
}

*/