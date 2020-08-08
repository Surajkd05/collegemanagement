package com.college.automation.system.dtos;

import java.util.Set;

public class PlacementDetailsDto {



    private String studentName;

    private String mobileNo;

    private String email;

    Set<CompanyDetailsDto> companyDetailsDtos;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<CompanyDetailsDto> getCompanyDetailsDtos() {
        return companyDetailsDtos;
    }

    public void setCompanyDetailsDtos(Set<CompanyDetailsDto> companyDetailsDtos) {
        this.companyDetailsDtos = companyDetailsDtos;
    }
}
