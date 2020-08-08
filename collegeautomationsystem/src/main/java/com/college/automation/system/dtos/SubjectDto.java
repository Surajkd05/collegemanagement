package com.college.automation.system.dtos;

import javax.validation.constraints.NotEmpty;

public class SubjectDto {

    @NotEmpty(message = "Must provide Year")
    private int year;

    @NotEmpty(message = "Must provide subject name")
    private String subjectName;

    @NotEmpty(message = "Must provide subject code")
    private String subjectCode;

    private Long branchId;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}
