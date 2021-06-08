package com.oracle.entity;
public class Result {
    private Integer resid;
    private Integer purid;
    private String resname;
    private Integer resstatus;
    public Result() {
        super();
    }
    public Result(Integer resid,Integer purid,String resname,Integer resstatus) {
        super();
        this.resid = resid;
        this.purid = purid;
        this.resname = resname;
        this.resstatus = resstatus;
    }
    public Integer getResid() {
        return this.resid;
    }

    public void setResid(Integer resid) {
        this.resid = resid;
    }

    public Integer getPurid() {
        return this.purid;
    }

    public void setPurid(Integer purid) {
        this.purid = purid;
    }

    public String getResname() {
        return this.resname;
    }

    public void setResname(String resname) {
        this.resname = resname;
    }

    public Integer getResstatus() {
        return this.resstatus;
    }

    public void setResstatus(Integer resstatus) {
        this.resstatus = resstatus;
    }

}
