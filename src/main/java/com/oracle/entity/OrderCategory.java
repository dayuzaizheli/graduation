package com.oracle.entity;
public class OrderCategory {
    private Integer ordcatid;
    private Integer ordid;
    private Integer catid;
    private Double catnum;
    private Double total;
    public OrderCategory() {
        super();
    }
    public OrderCategory(Integer ordcatid,Integer ordid,Integer catid,Double catnum,Double total) {
        super();
        this.ordcatid = ordcatid;
        this.ordid = ordid;
        this.catid = catid;
        this.catnum = catnum;
        this.total = total;
    }
    public Integer getOrdcatid() {
        return this.ordcatid;
    }

    public void setOrdcatid(Integer ordcatid) {
        this.ordcatid = ordcatid;
    }

    public Integer getOrdid() {
        return this.ordid;
    }

    public void setOrdid(Integer ordid) {
        this.ordid = ordid;
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

    public Double getTotal(){
        return this.total;
    }

    public void setTotal(Double total){
        this.total = total;
    }

}
