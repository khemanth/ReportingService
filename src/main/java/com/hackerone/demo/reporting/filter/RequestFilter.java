package com.hackerone.demo.reporting.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RequestFilter implements Filter{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String accessToken = request.getHeader("ACCESS_TOKEN");
        if (null == accessToken || !accessToken.equalsIgnoreCase("NZGAGBDFQLN3CRZPF2O0")) {
            LOGGER.info("MSG=Invaild request");
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The ACCESS TOKEN is not valid");
        }

        filterChain.doFilter(request, response);
    }

}
