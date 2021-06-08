package com.oracle.entity;
public class Request {
    private Integer reqid;
    private String reqname;
    private Integer reqmember;
    private Integer reqstatus;
    private Integer reqlevel;
    private java.util.Date cretime;
    public Request() {
        super();
    }
    public Request(Integer reqid,String reqname,Integer reqmember,Integer reqstatus,Integer reqlevel, java.util.Date cretime) {
        super();
        this.reqid = reqid;
        this.reqname = reqname;
        this.reqmember = reqmember;
        this.reqstatus = reqstatus;
        this.reqlevel = reqlevel;
        this.cretime = cretime;
    }
    public Integer getReqid() {
        return this.reqid;
    }

    public void setReqid(Integer reqid) {
        this.reqid = reqid;
    }

    public String getReqname(){
        return this.reqname;
    }

    public void setReqname(String reqname){
        this.reqname = reqname;
    }

    public Integer getReqmember() {
        return this.reqmember;
    }

    public void setReqmember(Integer reqmember) {
        this.reqmember = reqmember;
    }

    public Integer getReqstatus() {
        return this.reqstatus;
    }

    public void setReqstatus(Integer reqstatus) {
        this.reqstatus = reqstatus;
    }

    public Integer getReqlevel() {
        return this.reqlevel;
    }

    public void setReqlevel(Integer reqlevel) {
        this.reqlevel = reqlevel;
    }

    public java.util.Date getCretime(){
        return this.cretime;
    }

    public void setCretime(java.util.Date cretime){
        this.cretime = cretime;
    }

}
