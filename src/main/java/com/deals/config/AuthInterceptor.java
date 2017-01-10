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

        String requestUri = httpServletRequest.getRequestURI();
        if (requestUri.startsWith("/BestDeals/rest/base/findAllCitiesByState") ||
                requestUri.startsWith("/BestDeals/rest/base/findAllTalukasByCityId") ||
                requestUri.startsWith("/BestDeals/rest/base/findAllVillagesByTalukaId")){
                return true;
        }

        if (requestUri.startsWith("/BestDeals/rest")){
            if( API_VALUE.equals(httpServletRequest.getHeader(API_KEY)) ||
                    httpServletRequest.getSession().getAttribute("userId") != null) {
                return true;
            } else {
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }

        // Avoid a redirect loop for some urls
        if( !requestUri.equals("/BestDeals/") &&
                !requestUri.equals("/BestDeals/login") &&
                !requestUri.equals("/BestDeals/login/") &&
                !requestUri.equals("/BestDeals/registerPage") &&
                !requestUri.equals("/BestDeals/registerPage/") &&
                !requestUri.equals("/BestDeals/admin") &&
                !requestUri.equals("/BestDeals/admin/")) {
            if( httpServletRequest.getSession().getAttribute("userId") == null) {
                if(log.isDebugEnabled()) log.debug("AuthInterceptor: invalid session for " + requestUri);
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
