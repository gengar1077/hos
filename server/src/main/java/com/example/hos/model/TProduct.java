package com.example.hos.model;

import java.io.Serializable;

public class TProduct implements Serializable {
    private Long pId;

    private Long sId;

    private String pName;

    private String place;

    private String spec;

    private Integer price;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName == null ? null : pName.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", pId=").append(pId);
        sb.append(", sId=").append(sId);
        sb.append(", pName=").append(pName);
        sb.append(", place=").append(place);
        sb.append(", spec=").append(spec);
        sb.append(", price=").append(price);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}