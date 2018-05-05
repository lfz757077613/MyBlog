package com.qunar.lfz;

import com.qunar.lfz.model.MyResponse;
import com.qunar.lfz.model.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    private MyResponse handlerException(Exception e) {
        log.error("未捕获的异常", e);
        return MyResponse.createResponse(ResponseEnum.UNKNOWN_ERROR);
    }
}