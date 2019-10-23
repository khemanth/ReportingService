package com.hackerone.demo.reporting.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hackerone.demo.reporting.filter.RequestFilter;


@Configuration
public class ApplicationConfig {

    @Bean
    public FilterRegistrationBean <RequestFilter> filterRegistrationBean() {
     FilterRegistrationBean < RequestFilter > registrationBean = new FilterRegistrationBean<RequestFilter>();
     RequestFilter customURLFilter = new RequestFilter();

     registrationBean.setFilter(customURLFilter);
     registrationBean.addUrlPatterns("/report/*");
     registrationBean.setOrder(2); //set precedence
     return registrationBean;
    }
}