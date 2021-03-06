package com.springcloud.article.Hander;

import entity.Result;
import entity.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理类
 *
 * @author: 许集思
 * @date: 2020/5/23 23:43
 */

@ControllerAdvice
public class BaseExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //做异常处理的拦截器
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        //打印日志
        logger.error("Request URL: {} , Exception: {} ", request.getRequestURL(), e.getMessage());
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
}
