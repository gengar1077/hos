package com.example.hos.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author changwei.zhong
 * @date create by 2021/3/2
 */
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 7074499206336506702L;

    @ApiModelProperty(value = "药品id")
    private String pid;

    @ApiModelProperty(value = "药品名")
    private String pName;
}
