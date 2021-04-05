package com.example.hos.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author changwei.zhong
 * @date create by 2021/3/8
 */
@Data
public class StockVO implements Serializable {

    private static final long serialVersionUID = 8783559686559197583L;

    @ApiModelProperty(value = "库存id")
    private String stockId;

    @ApiModelProperty(value = "药品id")
    private String pid;

    @ApiModelProperty(value = "药品名")
    private String pname;

    @ApiModelProperty(value = "药品数量")
    private Integer pNum;

    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    @ApiModelProperty(value = "药品信息")
    private ProductVO productVO;
}
