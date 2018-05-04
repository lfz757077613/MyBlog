package com.qunar.lfz.model;

public enum ResponseEnum {
    SUCC(0, "succ"),
    ALREAD_LOGIN(1, "already_login"),
    UNKNOWN_ERROR(-1,"unknown_error"),
    LOGIN_ERROR(-2,"login_error"),
    NON_LOGIN(-3, "non_login"),
    NON_PERM(-4, "non_perm"),
    NON_AUTH(-5, "non_auth"),
    ;
    private int code;
    private String message;

    ResponseEnum(int code, String message) {
        this.code=code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
