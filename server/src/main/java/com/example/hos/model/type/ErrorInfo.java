package com.example.hos.model.type;

/**
 * 异常错误枚举
 * @author xiongpingan
 * @date 2021/3/26
 */
public enum ErrorInfo {

    ACCOUNT_NOT_FOUND("507", "找不到账号"),

    PASSWORD_IS_FALSE("508", "密码错误"),

    ACCOUNT_IS_EXIST("545", "");

    private String code;

    private String message;

    ErrorInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
