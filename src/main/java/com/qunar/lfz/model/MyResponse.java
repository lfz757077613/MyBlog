package com.qunar.lfz.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyResponse<T> {
    private int code;
    private String message;
    private T date;

    public static final MyResponse NON_LOGIN = MyResponse.createResponse(ResponseEnum.NON_LOGIN);
    public static final MyResponse NON_PERM = MyResponse.createResponse(ResponseEnum.NON_PERM);
    public static final MyResponse NON_AUTH = MyResponse.createResponse(ResponseEnum.NON_AUTH);

    private MyResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> MyResponse<T> createResponse(ResponseEnum responseType) {
        return new MyResponse<>(
                responseType.getCode(), responseType.getMessage());
    }

}
