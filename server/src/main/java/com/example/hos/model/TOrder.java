package com.example.hos.model;

import java.io.Serializable;
import java.util.Date;

public class TOrder implements Serializable {
    private Long oId;

    private Long money;

    private Integer pNum;

    private Short payType;

    private Short status;

    private String operator;

    private String remark;

    private Date createtime;

    private static final long serialVersionUID = 1L;

    public Long getoId() {
        return oId;
    }

    public void setoId(Long oId) {
        this.oId = oId;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Integer getpNum() {
        return pNum;
    }

    public void setpNum(Integer pNum) {
        this.pNum = pNum;
    }

    public Short getPayType() {
        return payType;
    }

    public void setPayType(Short payType) {
        this.payType = payType;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", oId=").append(oId);
        sb.append(", money=").append(money);
        sb.append(", pNum=").append(pNum);
        sb.append(", payType=").append(payType);
        sb.append(", status=").append(status);
        sb.append(", operator=").append(operator);
        sb.append(", remark=").append(remark);
        sb.append(", createtime=").append(createtime);
        sb.append("]");
        return sb.toString();
    }
}