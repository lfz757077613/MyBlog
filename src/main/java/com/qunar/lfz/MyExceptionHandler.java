package com.qunar.lfz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(Exception.class)
    private String handlerException(Exception e) {
        log.error("未捕获的异常", e);
        return "/static/html/error.html";
    }
}