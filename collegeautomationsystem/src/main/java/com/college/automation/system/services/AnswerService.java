package com.college.automation.system.services;

import com.college.automation.system.dtos.AnswerDto;
import com.college.automation.system.dtos.AnswerViewDto;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.Answers;
import com.college.automation.system.model.Questions;
import com.college.automation.system.repos.AnswersRepo;
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
public class AnswerService {

    @Autowired
    private QuestionsRepo questionsRepo;

    @Autowired
    private AnswersRepo answersRepo;

    @Autowired
    private MessageSource messageSource;

    /*
    *
    *  Add answer for question
    *
    */

    public String addAnswer(Long questionId, AnswerDto answerDto){
        Optional<Questions> questions = questionsRepo.findById(questionId);

        if(questions.isPresent()){

            Answers answers = new Answers();

            answers.setQuestions(questions.get());
            answers.setAnswer(answerDto.getAnswer());

            answersRepo.save(answers);

            return messageSource.getMessage("Answer.added", null, LocaleContextHolder.getLocale());
        }else {
            throw new NotFoundException(messageSource.getMessage("NotFound.question", null, LocaleContextHolder.getLocale()));
        }
    }

    /*
    *
    *  get answers for question
    *
    */

    public Set<AnswerViewDto> getAnswers(Long questionId, String  page, String size, String sortBy){
        Optional<Questions> questions = questionsRepo.findById(questionId);

        if(questions.isPresent()){
            List<Answers> answersSet= answersRepo.findAllByQuestion(questionId, PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(sortBy).descending()));

            if(answersSet != null){
                Set<AnswerViewDto> answerViewDtos = new LinkedHashSet<>();

                for(Answers answers : answersSet){
                    AnswerViewDto answerViewDto = new AnswerViewDto();

                    answerViewDto.setAnswer(answers.getAnswer());
                    answerViewDto.setAnswerId(answers.getAnswerId());

                    answerViewDtos.add(answerViewDto);
                }

                return answerViewDtos;
            }else {
                throw new NotFoundException(messageSource.getMessage("NotFound.answer", null, LocaleContextHolder.getLocale()));
            }
        }
        return null;
    }

    /*
    *
    * Edit existing answer
    *
    */

    public String updateExistingAnswer(Long answerId, AnswerDto answerDto){

        Optional<Answers> answers = answersRepo.findById(answerId);

        if(answers.isPresent()){
            answers.get().setAnswer(answerDto.getAnswer());
            answersRepo.save(answers.get());

            return messageSource.getMessage("Answer.updated", null, LocaleContextHolder.getLocale());
        }else {
            throw new NotFoundException(messageSource.getMessage("NotFound.answer", null, LocaleContextHolder.getLocale()));
        }
    }

    /*
    *
    * Delete existing answer
    *
    */

    public void deleteExistingAnswer(Long answerId){
        Optional<Answers> answers = answersRepo.findById(answerId);

        if(answers.isPresent()){
            answersRepo.deleteById(answerId);
        }
    }
}
