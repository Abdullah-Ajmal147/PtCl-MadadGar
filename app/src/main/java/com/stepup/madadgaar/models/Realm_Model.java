package com.stepup.madadgaar.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Realm_Model extends RealmObject {

    private String username;
    @PrimaryKey
    private String empnum;
    private String email;
    private String password;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid;
    private long tcc;

    public long getTcc() {
        return tcc;
    }

    public void setTcc(long tcc) {
        this.tcc = tcc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmpnum() {
        return empnum;
    }

    public void setEmpnum(String empnum) {
        this.empnum = empnum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
