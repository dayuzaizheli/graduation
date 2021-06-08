package com.oracle.entity;
public class Supplier {
    private Integer supid;
    private String supname;
    private Integer supagent;
    private Double supnumber;
    private java.util.Date supregtime;
    private Integer supstatus;
    public Supplier() {
        super();
    }
    public Supplier(Integer supid,String supname,Integer supagent,Double supnumber,java.util.Date supregtime,Integer supstatus) {
        super();
        this.supid = supid;
        this.supname = supname;
        this.supagent = supagent;
        this.supnumber = supnumber;
        this.supregtime = supregtime;
        this.supstatus = supstatus;
    }
    public Integer getSupid() {
        return this.supid;
    }

    public void setSupid(Integer supid) {
        this.supid = supid;
    }

    public String getSupname() {
        return this.supname;
    }

    public void setSupname(String supname) {
        this.supname = supname;
    }

    public Integer getSupagent() {
        return this.supagent;
    }

    public void setSupagent(Integer supagent) {
        this.supagent = supagent;
    }

    public Double getSupnumber() {
        return this.supnumber;
    }

    public void setSupnumber(Double supnumber) {
        this.supnumber = supnumber;
    }

    public java.util.Date getSupregtime() {
        return this.supregtime;
    }

    public void setSupregtime(java.util.Date supregtime) {
        this.supregtime = supregtime;
    }

    public Integer getSupstatus() {
        return this.supstatus;
    }

    public void setSupstatus(Integer supstatus) {
        this.supstatus = supstatus;
    }

}
