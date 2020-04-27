package com.springcloud.base.handler;


import entity.Result;
import entity.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

//拦截所有带@Controller的东西
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
