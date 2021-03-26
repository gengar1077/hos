package com.example.hos.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;


/**
 * 响应返回对象
 * @author 吃面龙
 */
@Data
public class ResultResponse implements Serializable {

    private static final long serialVersionUID = 7235084927681911824L;

    private Boolean success;

    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_NULL)
    private String returnCode;

    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_NULL)
    private Object returnData;

    public ResultResponse success(String message){
        this.setSuccess(true);
        this.setReturnCode(Code.SUCCESS);
        this.setMessage(message);
        return this;
    }

    public ResultResponse requireLogin(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.REQUIRE_LOGIN);
        this.setMessage(message);
        return this;
    }

    public ResultResponse fail(String code,String message){
        this.setSuccess(false);
        this.setReturnCode(code);
        this.setMessage(message);
        return this;
    }

    public void success() {
    }

    public static class Code{

        public static final String SUCCESS="200";

        public static final String REQUIRE_LOGIN="508";

    }
}
