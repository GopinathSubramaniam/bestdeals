package com.deals.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by gsitgithub on 15/11/2016.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    HandlerInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (authInterceptor != null)
            registry.addInterceptor(authInterceptor);

//        registry.addInterceptor(getYourInterceptor());
//        registry.addInterceptor(yourInjectedInceptor);
        // next two should be avoid -- tightly coupled and not very testable
        /*registry.addInterceptor(new YourInceptor());
        registry.addInterceptor(new HandlerInterceptor() {
            ...
        });*/
    }
}
