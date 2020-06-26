package com.college.automation.system.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UpdateStudentProfileDto {

    @NotEmpty(message = "Must provide your first name")
    private String firstName;

    @NotEmpty(message = "Must provide your last name")
    private String lastName;

    @NotEmpty(message = "Enter your mobile number")
    @Pattern(regexp = "\\d{10}", message = "Mobile number is invalid")
    private String mobileNo;

    private String branch;

    private int year;

    private int section;

    private int semester;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}

