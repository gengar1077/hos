package com.example.hos.model;

import java.io.Serializable;
import java.util.Date;

public class TStock implements Serializable {
    private String stockId;

    private String pId;

    private String pName;

    private Integer pNum;

    private Integer selNum;

    private Date createtime;

    private String status;

    private static final long serialVersionUID = 1L;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId == null ? null : stockId.trim();
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId == null ? null : pId.trim();
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
        sb.append(", createtime=").append(createtime);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }
}