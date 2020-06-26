package com.college.automation.system.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branches branches;

    @Column(length = 2500)
    private String question;

    @OneToMany(mappedBy = "questions", cascade = CascadeType.ALL)
    private Set<Answers> answersSet;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Branches getBranches() {
        return branches;
    }

    public void setBranches(Branches branches) {
        this.branches = branches;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<Answers> getAnswersSet() {
        return answersSet;
    }

    public void setAnswersSet(Set<Answers> answersSet) {
        this.answersSet = answersSet;
    }
}
