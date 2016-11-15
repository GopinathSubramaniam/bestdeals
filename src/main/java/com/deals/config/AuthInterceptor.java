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
    static Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.info("AuthInterceptor: Pre-handle");

        // Avoid a redirect loop for some urls
        if( !httpServletRequest.getRequestURI().equals("/BestDeals/") &&
                !httpServletRequest.getRequestURI().equals("/BestDeals/login") &&
                !httpServletRequest.getRequestURI().equals("/BestDeals/login/") &&
                !httpServletRequest.getRequestURI().equals("/BestDeals/admin") &&
                !httpServletRequest.getRequestURI().equals("/BestDeals/admin/") &&
                !httpServletRequest.getRequestURI().startsWith("/BestDeals/rest")
            /*!httpServletRequest.getRequestURI().equals("/sample-interc/") &&
                !httpServletRequest.getRequestURI().equals("/sample-interc/login.do") &&
                !httpServletRequest.getRequestURI().equals("/sample-interc/login.failed")*/)
        {
            if( httpServletRequest.getSession().getAttribute("userId") == null) {
                log.info("AuthInterceptor: invalid session");
                httpServletResponse.sendRedirect("/BestDeals/");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
