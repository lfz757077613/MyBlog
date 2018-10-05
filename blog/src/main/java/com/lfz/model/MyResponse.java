package com.lfz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyResponse<T> {
    private int code;
    private String message;
    private T data;

    private MyResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> MyResponse<T> createResponse(ResponseEnum responseType) {
        return new MyResponse<>(responseType.getCode(), responseType.getMessage());
    }

    public static <T> MyResponse<T> createResponse(ResponseEnum responseType, T t) {
        return new MyResponse<>(responseType.getCode(), responseType.getMessage(), t);
    }
}
