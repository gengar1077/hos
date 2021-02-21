package com.example.hos.model;

import java.io.Serializable;

public class TSupplier implements Serializable {
    private Long sId;

    private String pName;

    private String tel;

    private String address;

    private static final long serialVersionUID = 1L;

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sId=").append(sId);
        sb.append(", pName=").append(pName);
        sb.append(", tel=").append(tel);
        sb.append(", address=").append(address);
        sb.append("]");
        return sb.toString();
    }
}