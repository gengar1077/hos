package com.example.hos.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SellVO implements Serializable {

    private static final long serialVersionUID = -901541762579003497L;

    @ApiModelProperty(value = "销售id")
    private String sellId;

    @ApiModelProperty(value = "药品价格")
    private Long money;

    @ApiModelProperty(value = "药品名称")
    private String pname;

    @ApiModelProperty(value = "药品数量")
    private Integer pNum;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "简介")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private Date createtime;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "药品信息")
    private ProductVO productVO;

}