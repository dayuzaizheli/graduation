package com.oracle.entity;
public class PurchaseCategory {
    private Integer purcatid;
    private Integer purid;
    private Integer catid;
    private Double catnum;
    private Integer catsupplier;
    private Double total;
    public PurchaseCategory() {
        super();
    }
    public PurchaseCategory(Integer purcatid,Integer purid,Integer catid,Double catnum,Integer catsupplier,Double total) {
        super();
        this.purcatid = purcatid;
        this.purid = purid;
        this.catid = catid;
        this.catnum = catnum;
        this.catsupplier = catsupplier;
        this.total = total;
    }
    public Integer getPurcatid() {
        return this.purcatid;
    }

    public void setPurcatid(Integer purcatid) {
        this.purcatid = purcatid;
    }

    public Integer getPurid() {
        return this.purid;
    }

    public void setPurid(Integer purid) {
        this.purid = purid;
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

    public Integer getCatsupplier() {
        return this.catsupplier;
    }

    public void setCatsupplier(Integer catsupplier) {
        this.catsupplier = catsupplier;
    }

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}
