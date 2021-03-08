package com.example.hos.model;

import java.io.Serializable;
import java.util.Date;

public class TStock implements Serializable {
    private Long stockId;

    private Long pId;

    private String pName;

    private Integer pNum;

    private Integer selNum;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName == null ? null : pName.trim();
    }

    public Integer getpNum() {
        return pNum;
    }

    public void setpNum(Integer pNum) {
        this.pNum = pNum;
    }

    public Integer getSelNum() {
        return selNum;
    }

    public void setSelNum(Integer selNum) {
        this.selNum = selNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", stockId=").append(stockId);
        sb.append(", pId=").append(pId);
        sb.append(", pName=").append(pName);
        sb.append(", pNum=").append(pNum);
        sb.append(", selNum=").append(selNum);
        sb.append(", createtime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}