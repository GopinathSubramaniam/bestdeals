package com.deals.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gsitgithub on 15/11/2016.
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    final String API_KEY = "API_KEY";
    final String API_VALUE = "ea74cea31ed48c6d020167980b1b1aba1815907ecf14ec7242d47f9cfd72fcdf";
    static Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        if(log.isDebugEnabled()) log.debug("AuthInterceptor: "+httpServletRequest.getRequestURI());

        if (httpServletRequest.getRequestURI().startsWith("/BestDeals/rest")){
            if( API_VALUE.equals(httpServletRequest.getHeader(API_KEY)) ||
                    httpServletRequest.getSession().getAttribute("userId") != null) {
                return true;
            }
        }

        // Avoid a redirect loop for some urls
        if( !httpServletRequest.getRequestURI().equals("/BestDeals/") &&
                !httpServletRequest.getRequestURI().equals("/BestDeals/login") &&
                !httpServletRequest.getRequestURI().equals("/BestDeals/login/") &&
                !httpServletRequest.getRequestURI().equals("/BestDeals/registerPage") &&
                !httpServletRequest.getRequestURI().equals("/BestDeals/registerPage/") &&
                !httpServletRequest.getRequestURI().equals("/BestDeals/admin") &&
                !httpServletRequest.getRequestURI().equals("/BestDeals/admin/")) {
            if( httpServletRequest.getSession().getAttribute("userId") == null) {
                log.info("AuthInterceptor: invalid session for " + httpServletRequest.getRequestURI());
                httpServletResponse.sendRedirect("/BestDeals/");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if(log.isDebugEnabled()) log.debug("AuthInterceptor: "+httpServletRequest.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
