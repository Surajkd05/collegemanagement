package com.college.automation.system.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonFilter("Employee-Filter")
public class Employee extends User{

    private String branch;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    /*    private String mobileNo;

    public String getEmpMobileNo() {
        return mobileNo;
    }

    public void setEmpMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }*/
}

