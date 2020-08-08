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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class QuestionService {

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private QuestionsRepo questionsRepo;

    @Autowired
    private MessageSource messageSource;

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

            return messageSource.getMessage("Question.added", null, LocaleContextHolder.getLocale());
        }else {
            throw new NotFoundException(messageSource.getMessage("NotFound.branch", null, LocaleContextHolder.getLocale()));
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
                Set<QuestionViewDto> questionViewDtos = new LinkedHashSet<>();
                for(Questions questions : questionsSet){
                    QuestionViewDto questionDto = new QuestionViewDto();

                    questionDto.setQuestionId(questions.getQuestionId());
                    questionDto.setQuestion(questions.getQuestion());

                    questionViewDtos.add(questionDto);
                }

                return questionViewDtos;
            }else {
                throw new BadRequestException(messageSource.getMessage("NotFound.questionByBranch", null, LocaleContextHolder.getLocale()));
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

            return messageSource.getMessage("Question.updated", null, LocaleContextHolder.getLocale());
        }else {
            throw new NotFoundException(messageSource.getMessage("NotFound.question", null, LocaleContextHolder.getLocale()));
        }
    }
}
