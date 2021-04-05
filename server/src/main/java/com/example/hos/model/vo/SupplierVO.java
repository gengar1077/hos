package com.example.hos.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SupplierVO implements Serializable {

    private static final long serialVersionUID = 1930266614139756606L;

    @ApiModelProperty(value = "供应商id")
    private String sid;

    @ApiModelProperty(value = "供应商名称")
    private String sname;

    @ApiModelProperty(value = "药品名")
    private String pname;

    @ApiModelProperty(value = "供应商号码")
    private String tel;

    @ApiModelProperty(value = "供应商地址")
    private String address;

    @ApiModelProperty(value = "供应商状态")
    private String status;

    @ApiModelProperty(value = "药品信息")
    private ProductVO productVO;
}