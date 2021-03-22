package com.example.hos.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author changwei.zhong
 * @date create by 2021/3/8
 */
@Data
public class StockVO {
    @ApiModelProperty(value = "药品id")
    private String pid;

    @ApiModelProperty(value = "药品名")
    private String pName;

    private List<ProductVO> productVOList;
}
