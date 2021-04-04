package com.example.hos.model.type;

/**
 * 异常错误枚举
 * @date 2021/3/26
 */
public enum ErrorInfo {

    ACCOUNT_NOT_FOUND("507", "找不到账号"),

    PASSWORD_IS_FALSE("508", "密码错误"),

    REQUIRE_LOGIN("509", "请登录"),

    ACCOUNT_IS_EXIST("545", "用户已存在");

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
