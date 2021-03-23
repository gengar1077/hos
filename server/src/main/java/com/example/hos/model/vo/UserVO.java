package com.example.hos.model.vo;

import com.example.hos.model.TUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author changwei.zhong
 * @date create by 2021/2/24
 */
@Data
@ApiModel(description = "用户信息")
public class UserVO {
    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户头像")
    private String photo;

    @ApiModelProperty(value = "用户微博")
    private String wei;

    @ApiModelProperty(value = "用户角色")
    private String roleName;

    @ApiModelProperty(value = "用户简介")
    private String remark;

    /**
     * user转vo
     * @author changwei.zhong
     * @date 2021/1/11
     * @param user 用户
     * @return userVo
     **/
    public static UserVO makeVO(TUser user) {
        UserVO userVo = new UserVO();
        BeanUtils.copyProperties(user,userVo);
        return userVo;
    }
}
