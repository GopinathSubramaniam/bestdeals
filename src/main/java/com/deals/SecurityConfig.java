/*
package com.deals;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.deals.model.Login;
import com.deals.repository.LoginRepository;

@Component
public class SecurityConfig extends HandlerInterceptorAdapter{
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean b = false;
		
		System.out.println("URL ::: "+request.getRequestURI());
		String uri = request.getRequestURI();
		
		if(uri.startsWith("/") || uri.startsWith("/login")  ){
			b = true;
		}else{
			String token = request.getHeader("token");
			if(token!=null){
				Login login = loginRepository.findByToken(token);
				if(login != null) b = true;
			}
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
