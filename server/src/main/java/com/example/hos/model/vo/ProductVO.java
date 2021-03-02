package com.example.hos.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author changwei.zhong
 * @date create by 2021/3/2
 */
@Data
public class ProductVO {

    @ApiModelProperty(value = "药品id")
    private Long pid;

    @ApiModelProperty(value = "药品名")
    private String pName;
}
