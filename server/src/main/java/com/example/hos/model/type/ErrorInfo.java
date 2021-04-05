package com.example.hos.model.type;

/**
 * 异常错误枚举
 * @date 2021/3/26
 */
public enum ErrorInfo {

    ACCOUNT_NOT_FOUND("507", "找不到账号"),

    PASSWORD_IS_FALSE("508", "用户名或密码错误"),

    REQUIRE_LOGIN("509", "请登录"),

    ACCOUNT_IS_EXIST("545", "用户已存在"),

    PRODUCT_IS_EXIST("512", "药品已存在"),

    PRODUCT_NOT_FOUND("510", "找不到药品"),

    SUPPLIER_IS_EXIST("513", "供应商已存在"),

    SUPPLIER_NOT_FOUND("514", "找不到供应商"),

    DATA_ERROR("400", "数据异常"),

    STOCK_NOT_FOUND("511", "找不到库存");

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
