package com.college.automation.system.model;

import javax.persistence.*;

@Entity
public class InterviewTips {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long tipId;

    private String tip;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branches branches;

    public Long getTipId() {
        return tipId;
    }

    public void setTipId(Long tipId) {
        this.tipId = tipId;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Branches getBranches() {
        return branches;
    }

    public void setBranches(Branches branches) {
        this.branches = branches;
    }
}
