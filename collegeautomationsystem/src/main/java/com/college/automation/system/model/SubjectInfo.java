package com.college.automation.system.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
public class SubjectInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long infoId;

    private Long branchId;

    private Long subjectId;

    private int year;

    private int sectionId;

    @JsonManagedReference
    @OneToMany(mappedBy = "subjectInfo", cascade = CascadeType.ALL)
    private Set<SubjectInfoData> subjectInfoDataSet;

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public Set<SubjectInfoData> getSubjectInfoDataSet() {
        return subjectInfoDataSet;
    }

    public void setSubjectInfoDataSet(Set<SubjectInfoData> subjectInfoDataSet) {
        this.subjectInfoDataSet = subjectInfoDataSet;
    }
}
