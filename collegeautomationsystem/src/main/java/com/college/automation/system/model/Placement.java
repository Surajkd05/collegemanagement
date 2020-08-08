package com.college.automation.system.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Placement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long placementId;

    private int companies;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id")
    private Branches branches;

    private String studentName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    @JsonManagedReference
    @OneToMany(mappedBy = "placement", cascade = CascadeType.ALL)
    private Set<PlacementData> placementDataSet;

    public Long getPlacementId() {
        return placementId;
    }

    public void setPlacementId(Long placementId) {
        this.placementId = placementId;
    }

    public int getCompanies() {
        return companies;
    }

    public void setCompanies(int companies) {
        this.companies = companies;
    }

    public Branches getBranches() {
        return branches;
    }

    public void setBranches(Branches branches) {
        this.branches = branches;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Set<PlacementData> getPlacementDataSet() {
        return placementDataSet;
    }

    public void setPlacementDataSet(Set<PlacementData> placementDataSet) {
        this.placementDataSet = placementDataSet;
    }

}
