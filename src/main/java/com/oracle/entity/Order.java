package com.oracle.entity;
public class Order {
    private Integer ordid;
    private String ordname;
    private Integer ordpurid;
    private Integer ordsupplier;
    private Integer ordmember;
    private Integer ordmemsup;
    private java.util.Date ordcretime;
    private java.util.Date ordbegintime;
    private java.util.Date ordcontimsup;
    private java.util.Date ordcontime;
    private Integer ordstatus;
    private Double total;
    private Integer ordcon;
    public Order() {
        super();
    }
    public Order(Integer ordid,String ordname,Integer ordpurid,Integer ordsupplier,Integer ordmember,Integer ordmemsup,java.util.Date ordcretime,java.util.Date ordbegintime,java.util.Date ordcontimsup,java.util.Date ordcontime,Integer ordstatus,Double total,Integer ordcon) {
        super();
        this.ordid = ordid;
        this.ordname = ordname;
        this.ordpurid = ordpurid;
        this.ordsupplier = ordsupplier;
        this.ordmember = ordmember;
        this.ordmemsup = ordmemsup;
        this.ordcretime = ordcretime;
        this.ordbegintime = ordbegintime;
        this.ordcontimsup = ordcontimsup;
        this.ordcontime = ordcontime;
        this.ordstatus = ordstatus;
        this.total = total;
        this.ordcon = ordcon;
    }
    public Integer getOrdid() {
        return this.ordid;
    }

    public void setOrdid(Integer ordid) {
        this.ordid = ordid;
    }

    public String getOrdname() {
        return this.ordname;
    }

    public void setOrdname(String ordname) {
        this.ordname = ordname;
    }

    public Integer getOrdpurid(){
        return this.ordpurid;
    }

    public void setOrdpurid(Integer ordpurid){
        this.ordpurid = ordpurid;
    }

    public Integer getOrdsupplier() {
        return this.ordsupplier;
    }

    public void setOrdsupplier(Integer ordsupplier) {
        this.ordsupplier = ordsupplier;
    }

    public Integer getOrdmember() {
        return this.ordmember;
    }

    public void setOrdmember(Integer ordmember) {
        this.ordmember = ordmember;
    }

    public Integer getOrdmemsup() {
        return this.ordmemsup;
    }

    public void setOrdmemsup(Integer ordmemsup) {
        this.ordmemsup = ordmemsup;
    }

    public java.util.Date getOrdcretime() {
        return this.ordcretime;
    }

    public void setOrdcretime(java.util.Date ordcretime) {
        this.ordcretime = ordcretime;
    }

    public java.util.Date getOrdbegintime() {
        return this.ordbegintime;
    }

    public void setOrdbegintime(java.util.Date ordbegintime) {
        this.ordbegintime = ordbegintime;
    }

    public java.util.Date getOrdcontimsup() {
        return this.ordcontimsup;
    }

    public void setOrdcontimsup(java.util.Date ordcontimsup) {
        this.ordcontimsup = ordcontimsup;
    }

    public java.util.Date getOrdcontime() {
        return this.ordcontime;
    }

    public void setOrdcontime(java.util.Date ordcontime) {
        this.ordcontime = ordcontime;
    }

    public Integer getOrdstatus() {
        return this.ordstatus;
    }

    public void setOrdstatus(Integer ordstatus) {
        this.ordstatus = ordstatus;
    }

    public Double getTotal(){
        return this.total;
    }

    public void setTotal(Double total){
        this.total = total;
    }

    public Integer getOrdcon(){
        return this.ordcon;
    }

    public void setOrdcon(Integer ordcon){
        this.ordcon = ordcon;
    }
}
