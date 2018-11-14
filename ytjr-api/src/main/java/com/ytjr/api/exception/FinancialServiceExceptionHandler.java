package com.ytjr.api.exception;

import com.ytjr.api.utils.R;
import com.ytjr.common.enums.ResponseEnums;
import com.ytjr.common.exception.FinancialServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class FinancialServiceExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(FinancialServiceExceptionHandler.class);

    /**
     * 自定义异常
     */
    @ExceptionHandler(FinancialServiceException.class)
    public R handleFinancialException(FinancialServiceException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("发现异常 url ={} ,message ={}", requestURI, e.getMsg());
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        return r;
    }

    /**
     * 请求参数类型错误异常的捕获
     */
    @ExceptionHandler(value = {BindException.class})
    public R badRequest(BindException e) {
        logger.error("请求参数绑定错误,message ={}", e.getMessage());
        return R.error(ResponseEnums.BAD_REQUEST.getCode(), ResponseEnums.BAD_REQUEST.getMsg());
    }

    /**
     * 404错误异常的捕获
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public R badRequestNotFound(NoHandlerFoundException e) {
        logger.error("找不到请求路径 ,message ={}", e.getMessage());
        return R.error(ResponseEnums.NOT_FOUND.getCode(), ResponseEnums.NOT_FOUND.getMsg());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return R.error();
    }
}
