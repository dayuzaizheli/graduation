package com.oracle.entity;
public class Category {
    private Integer catid;
    private String catname;
    private Integer catlevel;
    private String catsuper;
    private String catunit;
    private Integer catstatus;
    public Category() {
        super();
    }
    public Category(Integer catid,String catname,Integer catlevel,String catsuper,String catunit) {
        super();
        this.catid = catid;
        this.catname = catname;
        this.catlevel = catlevel;
        this.catsuper = catsuper;
        this.catunit = catunit;
    }
    public Integer getCatid() {
        return this.catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public String getCatname() {
        return this.catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public Integer getCatlevel() {
        return this.catlevel;
    }

    public void setCatlevel(Integer catlevel) {
        this.catlevel = catlevel;
    }

    public String getCatsuper() {
        return this.catsuper;
    }

    public void setCatsuper(String catsuper) {
        this.catsuper = catsuper;
    }

    public String getCatunit() {
        return this.catunit;
    }

    public void setCatunit(String catunit) {
        this.catunit = catunit;
    }

    public Integer getCatstatus() {
        return this.catstatus;
    }

    public void setCatstatus(Integer catstatus) {
        this.catstatus= catstatus;
    }


}
