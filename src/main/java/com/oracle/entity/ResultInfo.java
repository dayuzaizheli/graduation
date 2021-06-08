package com.oracle.entity;
public class ResultInfo {
    private Integer resinfid;
    private Integer resid;
    private Integer catid;
    private Double catnum;
    private Integer supplier;
    public ResultInfo() {
        super();
    }
    public ResultInfo(Integer resinfid,Integer resid,Integer catid,Double catnum,Integer supplier) {
        super();
        this.resinfid = resinfid;
        this.resid = resid;
        this.catid = catid;
        this.catnum = catnum;
        this.supplier = supplier;
    }
    public Integer getResinfid() {
        return this.resinfid;
    }

    public void setResinfid(Integer resinfid) {
        this.resinfid = resinfid;
    }

    public Integer getResid() {
        return this.resid;
    }

    public void setResid(Integer resid) {
        this.resid = resid;
    }

    public Integer getCatid() {
        return this.catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public Double getCatnum() {
        return this.catnum;
    }

    public void setCatnum(Double catnum) {
        this.catnum = catnum;
    }

    public Integer getSupplier() {
        return this.supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

}
