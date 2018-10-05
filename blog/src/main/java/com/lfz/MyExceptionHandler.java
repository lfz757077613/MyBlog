package com.lfz;

import com.lfz.model.MyResponse;
import com.lfz.model.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;


/**
 * 全局异常处理器，未捕获的异常统一返回未知错误
 */
@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(Exception.class)
    private MyResponse handlerException(Exception e) {
        log.error("未捕获的异常", e);
        return MyResponse.createResponse(ResponseEnum.UNKNOWN_ERROR);
    }

    //统一处理使用validation的参数错误异常
    @ExceptionHandler(ConstraintViolationException.class)
    private MyResponse handlerParamException(Exception e) {
        return MyResponse.createResponse(ResponseEnum.ILLEGAL_PARAM, e.getMessage());
    }
}