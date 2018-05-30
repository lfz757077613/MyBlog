package com.qunar.lfz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MyResponse<T> {
    private int code;
    private String message;
    private T data;

    private MyResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> MyResponse<T> createResponse(@NonNull ResponseEnum responseType) {
        return new MyResponse<>(responseType.getCode(), responseType.getMessage());
    }

    public static <T> MyResponse<T> createResponse(@NonNull ResponseEnum responseType, @NonNull T t) {
        return new MyResponse<>(responseType.getCode(), responseType.getMessage(), t);
    }
}
