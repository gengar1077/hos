package com.example.hos.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * @author: YU Qilin
 * @description: 通用响应返回对象
 * @date: 2020/5/29 10:47
 */
@SuppressWarnings("ALL")
@ApiModel(description = "通用响应返回对象")
@Data
public class JsonResult<T> {

    @ApiModelProperty(value = "结果状态", example = "true")
    private Boolean result;

    @ApiModelProperty(value = "结果代码", example = "200")
    private String code;

    @ApiModelProperty(value = "结果状态信息", example = "成功")
    private String msg;

    @ApiModelProperty(value = "结果数据")
    private T data;

    public JsonResult(Boolean result, String code, String msg) {
        this.result = result;
        this.code = code;
        this.msg = msg;
    }

    public static final String SUCCESS="200";

    public static final String OK="200";

    public static final String REQUIRE_LOGIN="508";

    /**
     * @author: YU Qilin
     * @description: 生成返回结果
     * @date: 2020/5/29 15:17
     */
    public static <T> JsonResult<T> ok(@Nullable T data) {
        JsonResult result = new JsonResult(true, SUCCESS, OK);
        result.setData(data);
        return result;
    }

    /**
     * 生成返回结果
     *
     * @param <T>
     * @return
     */
    public static <T> JsonResult<T> ok() {
        return new JsonResult(true, SUCCESS, OK);
    }

}