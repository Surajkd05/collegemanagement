package com.college.automation.system.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Branches {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long branchId;

    private String branchName;

    @OneToMany(mappedBy = "branches", cascade = CascadeType.ALL)
    private Set<Questions> questions;

    @OneToMany(mappedBy = "branches", cascade = CascadeType.ALL)
    private Set<InterviewTips> interviewTipsSet;

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Set<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Questions> questions) {
        this.questions = questions;
    }

    public Set<InterviewTips> getInterviewTipsSet() {
        return interviewTipsSet;
    }

    public void setInterviewTipsSet(Set<InterviewTips> interviewTipsSet) {
        this.interviewTipsSet = interviewTipsSet;
    }
}
