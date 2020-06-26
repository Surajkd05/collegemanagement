package com.college.automation.system.services;

import com.college.automation.system.dtos.QuestionDto;
import com.college.automation.system.dtos.QuestionViewDto;
import com.college.automation.system.exceptions.BadRequestException;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.Branches;
import com.college.automation.system.model.Questions;
import com.college.automation.system.repos.BranchRepo;
import com.college.automation.system.repos.QuestionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class QuestionService {

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private QuestionsRepo questionsRepo;

    /*
    *
    *  Add question by branch
    *
    */
    public String addQuestionByBranch(Long branchId, QuestionDto questionDto){
        Optional<Branches> branch = branchRepo.findById(branchId);

        if(branch.isPresent()){
            Questions questions = new Questions();

            questions.setQuestion(questionDto.getQuestion());
            questions.setBranches(branch.get());

            questionsRepo.save(questions);

            return "Question added successfully";
        }else {
            throw new NotFoundException("Branch not found");
        }
    }

    /*
    *
    * Get question by branch
    *
    */

    public Set<QuestionViewDto> getQuestionsByBranch(Long branchId, String  page, String size, String sortBy){

            List<Questions> questionsSet = questionsRepo.findAllByBranch(branchId, PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(sortBy).descending()));

            if(questionsSet != null){
                Set<QuestionViewDto> questionViewDtos = new HashSet<>();
                for(Questions questions : questionsSet){
                    QuestionViewDto questionDto = new QuestionViewDto();

                    questionDto.setQuestionId(questions.getQuestionId());
                    questionDto.setQuestion(questions.getQuestion());

                    questionViewDtos.add(questionDto);
                }

                return questionViewDtos;
            }else {
                throw new BadRequestException("There is no question available for this branch");
            }
    }

    /*
     *
     * Edit existing question
     *
     */

    public String updateExistingAnswer(Long questionId, QuestionDto questionDto){

        Optional<Questions> questions = questionsRepo.findById(questionId);

        if(questions.isPresent()){
            questions.get().setQuestion(questionDto.getQuestion());

            questionsRepo.save(questions.get());

            return "Question updated successfully";
        }else {
            throw new NotFoundException("Question not found");
        }
    }
}
