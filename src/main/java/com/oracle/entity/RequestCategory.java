package com.oracle.entity;
public class RequestCategory {
    private Integer reqcatid;
    private Integer reqid;
    private Integer catid;
    private Double catnum;
    private Double catpurchasenum;
    public RequestCategory() {
        super();
    }
    public RequestCategory(Integer reqcatid,Integer reqid,Integer catid,Double catnum,Double catpurchasenum) {
        super();
        this.reqcatid = reqcatid;
        this.reqid = reqid;
        this.catid = catid;
        this.catnum = catnum;
        this.catpurchasenum = catpurchasenum;
    }
    public Integer getReqcatid() {
        return this.reqcatid;
    }

    public void setReqcatid(Integer reqcatid) {
        this.reqcatid = reqcatid;
    }

    public Integer getReqid() {
        return this.reqid;
    }

    public void setReqid(Integer reqid) {
        this.reqid = reqid;
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

    public Double getCatpurchasenum() {
        return this.catpurchasenum;
    }

    public void setCatpurchasenum(Double catpurchasenum) {
        this.catpurchasenum = catpurchasenum;
    }

}
