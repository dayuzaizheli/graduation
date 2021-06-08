package com.oracle.entity;
public class Member {
    private Integer memid;
    private String memname;
    private String memrealname;
    private String mempwd;
    private String memphone;
    private String mememail;
    private Integer memisinner;
    private Integer memposition;
    public Member() {
        super();
    }
    public Member(Integer memid,String memname,String memrealname,String mempwd,String memphone,String mememail,Integer memisinner,Integer memposition) {
        super();
        this.memid = memid;
        this.memname = memname;
        this.memrealname = memrealname;
        this.mempwd = mempwd;
        this.memphone = memphone;
        this.mememail = mememail;
        this.memisinner = memisinner;
        this.memposition = memposition;
    }
    public Integer getMemid() {
        return this.memid;
    }

    public void setMemid(Integer memid) {
        this.memid = memid;
    }

    public String getMemname() {
        return this.memname;
    }

    public void setMemname(String memname) {
        this.memname = memname;
    }

    public String getMemrealname(){
        return this.memrealname;
    }

    public void setMemrealname(String memrealname){
        this.memrealname = memrealname;
    }

    public String getMempwd() {
        return this.mempwd;
    }

    public void setMempwd(String mempwd) {
        this.mempwd = mempwd;
    }

    public String getMemphone() {
        return this.memphone;
    }

    public void setMemphone(String memphone) {
        this.memphone = memphone;
    }

    public String getMememail() {
        return this.mememail;
    }

    public void setMememail(String mememail) {
        this.mememail = mememail;
    }

    public Integer getMemisinner() {
        return this.memisinner;
    }

    public void setMemisinner(Integer memisinner) {
        this.memisinner = memisinner;
    }

    public Integer getMemposition() {
        return this.memposition;
    }

    public void setMemposition(Integer memposition) {
        this.memposition = memposition;
    }

}
