package com.example.springakkademo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AsyncInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.info(">>> preHandle ...");
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOGGER.info(">>> postHandle ...");
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 用于处理异步请求，当 Controller 中有异步请求方法的时候会触发该方法时，
     * 异步请求先执行 preHandle、
     * 然后执行 afterConcurrentHandlingStarted。
     * 异步线程完成之后执行 preHandle、postHandle、afterCompletion。
     * ---------------------
     * 作者：不夜城的油条
     * 来源：CSDN
     * 原文：https://blog.csdn.net/xiejiefeng333/article/details/84107907
     * 版权声明：本文为博主原创文章，转载请附上博文链接！
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler Object
     * @throws Exception Exception
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.info(">>> afterConcurrentHandlingStarted ...");
        super.afterConcurrentHandlingStarted(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOGGER.info(">>> afterCompletion ...");
        super.afterCompletion(request, response, handler, ex);
    }
}
