package com.example.hos.model;

import java.io.Serializable;

public class TRole implements Serializable {
    private String rId;

    private String rName;

    private String remark;

    private String status;

    private static final long serialVersionUID = 1L;

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId == null ? null : rId.trim();
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName == null ? null : rName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        sb.append(", rId=").append(rId);
        sb.append(", rName=").append(rName);
        sb.append(", remark=").append(remark);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }
}