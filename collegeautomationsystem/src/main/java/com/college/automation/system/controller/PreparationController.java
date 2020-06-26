package com.college.automation.system.controller;

import com.college.automation.system.dtos.*;
import com.college.automation.system.services.AnswerService;
import com.college.automation.system.services.BranchService;
import com.college.automation.system.services.InterviewService;
import com.college.automation.system.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/preparation")
public class PreparationController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private InterviewService interviewService;

    /*
    *
    * Get branches controller
    *
    */
    @GetMapping(path = "/branch")
    public Set<BranchViewDto> getBranches(){
        return branchService.getBranch();
    }

    /*
     *
     * Add question controller
     *
     */
    @PostMapping(path = "/question")
    public String addQuestion(@RequestBody QuestionDto questionDto, @RequestParam(value = "branchId") Long branchId){
        return questionService.addQuestionByBranch(branchId,questionDto);
    }

    /*
    *
    * Get questions by branch
    *
    */
    @GetMapping(path = "/question")
    public Set<QuestionViewDto> getQuestions(@RequestParam(value = "branchId") Long branchId,@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size, @RequestParam(defaultValue = "question_id") String sortBy){
        return questionService.getQuestionsByBranch(branchId, page, size, sortBy);
    }

    /*
    *
    * Update existing question
    *
    */
    @PutMapping(path = "/question")
    public String updateQuestion(@RequestParam(value="questionId") Long questionId, @RequestBody QuestionDto questionDto){
        return questionService.updateExistingAnswer(questionId,questionDto);
    }

    /*
     *
     * Add answer controller
     *
     */
    @PostMapping(path = "/answer")
    public String addAnswer(@RequestParam(value = "questionId") Long questionId,@RequestBody AnswerDto answerDto){
        return answerService.addAnswer(questionId,answerDto);
    }

    /*
    *
    * Get answer controller
    *
    */
    @GetMapping(path = "/answer")
    public Set<AnswerViewDto> getAnswer(@RequestParam(value = "questionId") Long questionId,@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size, @RequestParam(defaultValue = "answer_id") String sortBy){
        return answerService.getAnswers(questionId, page, size, sortBy);
    }

    /*
     *
     * Edit answer controller
     *
     */
    @PutMapping(path = "/answer")
    public String updateAnswer(@RequestParam(value = "answerId") Long answerId, @RequestBody AnswerDto answerDto){
        return answerService.updateExistingAnswer(answerId,answerDto);
    }

    /*
     *
     * Delete answer controller
     *
     */
    @DeleteMapping(path = "/answer")
    public void deleteAnswer(@RequestParam(value = "answerId") Long answerId){
        answerService.deleteExistingAnswer(answerId);
    }

    /*
    *
    * Add interview tip
    *
    */
    @PostMapping(path = "/interview")
    public String addInterviewTip(@RequestBody InterviewTipDto interviewTipDto, @RequestParam(value = "branchId") Long branchId){
        return interviewService.addInterviewByBranch(branchId,interviewTipDto);
    }

    /*
    *
    *  Get interview tip
    *
    */
    @GetMapping(path = "/interview")
    public Set<InterviewTipViewDto> getInterviewTips(@RequestParam(value = "branchId") Long branchId, @RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size, @RequestParam(defaultValue = "tip_id") String sortBy){
        return interviewService.getInterviewTipsByBranch(branchId, page, size, sortBy);
    }
}
