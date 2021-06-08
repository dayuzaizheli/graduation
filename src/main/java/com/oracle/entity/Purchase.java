package com.oracle.entity;
public class Purchase {
    private Integer purid;
    private String purname;
    private Integer purmember;
    private Integer purstatus;
    private Double total;
    private java.util.Date cretime;
    public Purchase() {
        super();
    }
    public Purchase(Integer purid,String purname,Integer purmember,Integer purstatus,Double total, java.util.Date cretime) {
        super();
        this.purid = purid;
        this.purname = purname;
        this.purmember = purmember;
        this.purstatus = purstatus;
        this.total = total;
        this.cretime = cretime;
    }
    public Integer getPurid() {
        return this.purid;
    }

    public void setPurid(Integer purid) {
        this.purid = purid;
    }

    public String getPurname(){
        return this.purname;
    }

    public void setPurname(String purname){
        this.purname = purname;
    }

    public Integer getPurmember() {
        return this.purmember;
    }

    public void setPurmember(Integer purmember) {
        this.purmember = purmember;
    }

    public Integer getPurstatus() {
        return this.purstatus;
    }

    public void setPurstatus(Integer purstatus) {
        this.purstatus = purstatus;
    }

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public java.util.Date getCretime(){
        return this.cretime;
    }

    public void setCretime(java.util.Date cretime){
        this.cretime = cretime;
    }
}
