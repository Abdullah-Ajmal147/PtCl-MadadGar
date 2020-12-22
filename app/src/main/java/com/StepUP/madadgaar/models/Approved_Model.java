package com.StepUP.madadgaar.models;

public class Approved_Model  {
   private String email;
   private String fulName;
    private String emp_no;
    private String region;
    private String password;
    private String mobileNumber;
    private String uid;
    private String category;
    private int tcc;
    public Approved_Model(){

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getTcc() {
        return tcc;
    }

    public void setTcc(int tcc) {
        this.tcc = tcc;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFulName() {
        return fulName;
    }

    public void setFulName(String fulName) {
        this.fulName = fulName;
    }

    public String getEmp_no() {
        return emp_no;
    }

    public void setEmp_no(String emp_no) {
        this.emp_no = emp_no;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
