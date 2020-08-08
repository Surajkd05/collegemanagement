package com.college.automation.system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonFilter("Employee-Filter")
public class Employee extends User{

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name ="branch_id")
    private Branches branches;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "employee_subject",joinColumns = @JoinColumn(name = "emp_id",referencedColumnName = "user_id")
            ,inverseJoinColumns = @JoinColumn(name = "subject_id",referencedColumnName = "subjectId"))
    private Set<Subject> subjects;

    public Branches getBranches() {
        return branches;
    }

    public void setBranches(Branches branches) {
        this.branches = branches;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}

