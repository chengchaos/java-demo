package com.example.springakkademo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.async.WebAsyncUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}, filterName = "myFilter", asyncSupported = true)
@Order(2)
public class MyFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        LOGGER.info("MyFilter init ............................... ");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        LOGGER.info("before doFilter");
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            if (isAsyncStarted((HttpServletRequest) servletRequest) || servletRequest.isAsyncStarted()) {
                LOGGER.info("AllFilter is async");
            }
            LOGGER.info("finally doFilter");
        }
    }

    @Override
    public void destroy() {

    }

    protected boolean isAsyncStarted(HttpServletRequest request) {

        return WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted();
    }
}
