package com.qunar.lfz.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class MyResponse<T> {
    private int code;
    private String message;
    private T date;

    private MyResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> MyResponse<T> createResponse(ResponseEnum responseType) {
        return new MyResponse<>(responseType.getCode(), responseType.getMessage());
    }

    public static <T> MyResponse<T> createResponse(ResponseEnum responseType, T t) {
        return new MyResponse<T>(responseType.getCode(), responseType.getMessage()).setDate(t);
    }
}
