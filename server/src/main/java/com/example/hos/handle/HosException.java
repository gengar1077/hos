package com.example.hos.handle;

import lombok.Data;

/**
 * 自定义异常类
 * @author changwei.zhong
 * @date create by 2021/3/25
 */
@Data
public class HosException extends RuntimeException{

    //异常代码
    private String code;

    private String raiseBy;

    public HosException(String message) {
        super(message);
    }

    public HosException(String code, String message){
        super(message);
        this.code=code;
    }
}
