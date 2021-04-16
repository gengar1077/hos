package com.example.hos.model.vo;

import com.example.hos.model.entity.Product;
import com.example.hos.model.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

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
    private String pname;

    @ApiModelProperty(value = "药品地点")
    private String place;

    @ApiModelProperty(value = "药品规格")
    private String spec;

    @ApiModelProperty(value = "药品价格")
    private Integer price;

    @ApiModelProperty(value = "药品描述")
    private String remark;

    @ApiModelProperty(value = "药品状态")
    private String status;

    /**
     * 转vo
     * @author changwei.zhong
     * @date 2021/1/11
     * @param product
     * @return userVo
     **/
    public static ProductVO makeVO(Product product) {
        ProductVO productVO = new ProductVO();
        productVO.setPid(product.getPid());
        productVO.setPname(product.getPname());
        productVO.setPrice(product.getPrice());
        productVO.setSpec(product.getSpec());
        productVO.setRemark(product.getRemark());
        productVO.setPlace(product.getPlace());
        return productVO;
    }
}
