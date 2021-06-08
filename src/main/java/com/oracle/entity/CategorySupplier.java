package com.oracle.entity;
public class CategorySupplier {
    private Integer catsupid;
    private Integer catid;
    private Integer supid;
    private Double price;
    public CategorySupplier() {
        super();
    }
    public CategorySupplier(Integer catsupid,Integer catid,Integer supid, Double price) {
        super();
        this.catsupid = catsupid;
        this.catid = catid;
        this.supid = supid;
        this.price = price;
    }
    public Integer getCatsupid() {
        return this.catsupid;
    }

    public void setCatsupid(Integer catsupid) {
        this.catsupid = catsupid;
    }

    public Integer getCatid() {
        return this.catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public Integer getSupid() {
        return this.supid;
    }

    public void setSupid(Integer supid) {
        this.supid = supid;
    }

    public Double getPrice(){return this.price;}

    public void setPrice(Double price) {
        this.price = price;
    }
}
