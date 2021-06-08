package com.oracle.entity;
public class Postion {
    private Integer posid;
    private String posname;
    private String posresp;
    private String pospower;
    private String posremark;
    public Postion() {
        super();
    }
    public Postion(Integer posid,String posname,String posresp,String pospower,String posremark) {
        super();
        this.posid = posid;
        this.posname = posname;
        this.posresp = posresp;
        this.pospower = pospower;
        this.posremark = posremark;
    }
    public Integer getPosid() {
        return this.posid;
    }

    public void setPosid(Integer posid) {
        this.posid = posid;
    }

    public String getPosname() {
        return this.posname;
    }

    public void setPosname(String posname) {
        this.posname = posname;
    }

    public String getPosresp() {
        return this.posresp;
    }

    public void setPosresp(String posresp) {
        this.posresp = posresp;
    }

    public String getPospower() {
        return this.pospower;
    }

    public void setPospower(String pospower) {
        this.pospower = pospower;
    }

    public String getPosremark() {
        return this.posremark;
    }

    public void setPosremark(String posremark) {
        this.posremark = posremark;
    }

}
