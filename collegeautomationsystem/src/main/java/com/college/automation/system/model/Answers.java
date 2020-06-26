package com.college.automation.system.model;

import javax.persistence.*;

@Entity
public class Answers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long answerId;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Questions questions;

    @Column(length=5000)
    private String answer;

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Questions getQuestions() {
        return questions;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
