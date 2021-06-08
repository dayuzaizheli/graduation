package com.oracle.entity;
public class Contract {
    private Integer conid;
    private String conname;
    private Integer consupplier;
    private String concontent;
    private Double conoriquota;
    private Double consurquota;
    private java.util.Date consgintime;
    private java.util.Date conbegintime;
    private java.util.Date conendtime;
    private Integer constatus;
    public Contract() {
        super();
    }
    public Contract(Integer conid,String conname,Integer consupplier,String concontent,Double conoriquota,Double consurquota,java.util.Date consgintime,java.util.Date conbegintime,java.util.Date conendtime,Integer constatus) {
        super();
        this.conid = conid;
        this.conname = conname;
        this.consupplier = consupplier;
        this.concontent = concontent;
        this.conoriquota = conoriquota;
        this.consurquota = consurquota;
        this.consgintime = consgintime;
        this.conbegintime = conbegintime;
        this.conendtime = conendtime;
        this.constatus = constatus;
    }
    public Integer getConid() {
        return this.conid;
    }

    public void setConid(Integer conid) {
        this.conid = conid;
    }

    public String getConname() {
        return this.conname;
    }

    public void setConname(String conname) {
        this.conname = conname;
    }

    public Integer getConsupplier() {
        return this.consupplier;
    }

    public void setConsupplier(Integer consupplier) {
        this.consupplier = consupplier;
    }

    public String getConcontent() {
        return this.concontent;
    }

    public void setConcontent(String concontent) {
        this.concontent = concontent;
    }

    public Double getConoriquota() {
        return this.conoriquota;
    }

    public void setConoriquota(Double conoriquota) {
        this.conoriquota = conoriquota;
    }

    public Double getConsurquota() {
        return this.consurquota;
    }

    public void setConsurquota(Double consurquota) {
        this.consurquota = consurquota;
    }

    public java.util.Date getConsgintime() {
        return this.consgintime;
    }

    public void setConsgintime(java.util.Date consgintime) {
        this.consgintime = consgintime;
    }

    public java.util.Date getConbegintime() {
        return this.conbegintime;
    }

    public void setConbegintime(java.util.Date conbegintime) {
        this.conbegintime = conbegintime;
    }

    public java.util.Date getConendtime() {
        return this.conendtime;
    }

    public void setConendtime(java.util.Date conendtime) {
        this.conendtime = conendtime;
    }

    public Integer getConstatus() {
        return this.constatus;
    }

    public void setConstatus(Integer constatus) {
        this.constatus = constatus;
    }

}
