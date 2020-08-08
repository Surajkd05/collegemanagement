package com.college.automation.system.services;

import com.college.automation.system.dtos.*;
import com.college.automation.system.exceptions.BadRequestException;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.*;
import com.college.automation.system.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private SubjectInfoRepo subjectInfoRepo;

    @Autowired
    private SubjectInfoDataRepo subjectInfoDataRepo;

    @Autowired
    private MessageSource messageSource;

    /*
    *
    * Add Subject service
    *
    */
    public String addSubjectByBranchAndYear(SubjectDto subjectDto){

        Optional<Branches> branches = branchRepo.findById(subjectDto.getBranchId());

        if(branches.isPresent()){
            Subject subject = new Subject();

            subject.setBranches(branches.get());
            subject.setSubjectCode(subjectDto.getSubjectCode());
            subject.setSubjectName(subjectDto.getSubjectName());
            subject.setYear(subjectDto.getYear());

            subjectRepo.save(subject);

            return messageSource.getMessage("Subject.added", null, LocaleContextHolder.getLocale());
        }
        return null;
    }

    /*
    *
    * Get Subjects
    *
    */
    public Set<SubjectViewDto> getSubjectsByBranchAndYear(Long branchId, int year){

        Set<Subject> subjectSet = subjectRepo.findSubjectByBranchAndYear(branchId,year);

        if(!subjectSet.isEmpty()){
            Set<SubjectViewDto> subjectViewDtos = new LinkedHashSet<>();

            for (Subject subject : subjectSet){
                SubjectViewDto subjectViewDto = new SubjectViewDto();

                subjectViewDto.setId(subject.getSubjectId());
                subjectViewDto.setSubjectCode(subject.getSubjectCode());
                subjectViewDto.setSubjectName(subject.getSubjectName());

                subjectViewDtos.add(subjectViewDto);
            }

            return  subjectViewDtos;
        }else {
            throw new NotFoundException(messageSource.getMessage("NotFound.record", null, LocaleContextHolder.getLocale()));
        }
    }

    /*
    *
    * Allocate subject to employees
    *
    */
    public String addEmployeeSubject(AllocateSubjectDto allocateSubjectDto){
        Employee employee = employeeRepo.findByUserId(allocateSubjectDto.getEmpId());

        if(null != employee){
            Optional<Subject> subject = subjectRepo.findById(allocateSubjectDto.getSubId());
            if(subject.isPresent()){
                Set<Subject> subjects = new LinkedHashSet<>();
                subjects.add(subject.get());
                employee.setSubjects(subjects);
                employeeRepo.save(employee);
                return messageSource.getMessage("Subject.allocated", null, LocaleContextHolder.getLocale());
            }else {
                throw new NotFoundException(messageSource.getMessage("Subject.NotExist", null, LocaleContextHolder.getLocale()));
            }
        }else {
            throw new BadRequestException(messageSource.getMessage("Employee.NotExist", null, LocaleContextHolder.getLocale()));
        }
    }

    /*
    *
    *Get subjects by employeeId
    *
    */
    public Set<SubjectViewDto> getSubjectsByEmployee(String username){

        Employee employee = employeeRepo.findByEmail(username);

        Set<SubjectViewDto> subjectViewDtos = new LinkedHashSet<>();
        if(null != employee.getSubjects()) {
            System.out.println("In if block");
            for (Subject subject : employee.getSubjects()) {
                SubjectViewDto subjectViewDto = new SubjectViewDto();

                subjectViewDto.setId(subject.getSubjectId());
                subjectViewDto.setSubjectName(subject.getSubjectName());
                subjectViewDto.setSubjectCode(subject.getSubjectCode());

                subjectViewDtos.add(subjectViewDto);
            }
            return subjectViewDtos;
        }else {
            throw new NotFoundException(messageSource.getMessage("Subject.NotAllocated", null, LocaleContextHolder.getLocale()));
        }
    }

    /*
    *
    * Add subject information
    *
    */
    public String addSubjectInformationSectionWise(SubjectInfoDto subjectInfoDto){
        Optional<Branches> branches = branchRepo.findById(subjectInfoDto.getBranchId());
        if(branches.isPresent()){
            SubjectInfo subjectInfoExist = subjectInfoRepo.findSubjectInfoUniqueData(subjectInfoDto.getBranchId(), subjectInfoDto.getYear(), subjectInfoDto.getSection(),subjectInfoDto.getSubjectId());

            if(null != subjectInfoExist){
                SubjectInfoData subjectInfoData = new SubjectInfoData();

                subjectInfoData.setDate(new Date());
                subjectInfoData.setData(subjectInfoDto.getData());
                subjectInfoData.setSubjectInfo(subjectInfoExist);

                subjectInfoDataRepo.save(subjectInfoData);

                return messageSource.getMessage("Subject.info", null, LocaleContextHolder.getLocale());
            }else {
                SubjectInfo subjectInfo = new SubjectInfo();

                subjectInfo.setBranchId(subjectInfoDto.getBranchId());
                subjectInfo.setSectionId(subjectInfoDto.getSection());
                subjectInfo.setYear(subjectInfoDto.getYear());
                subjectInfo.setSubjectId(subjectInfoDto.getSubjectId());

                SubjectInfo subjectInfo1 = subjectInfoRepo.save(subjectInfo);

                SubjectInfoData subjectInfoData = new SubjectInfoData();
                subjectInfoData.setSubjectInfo(subjectInfo1);
                subjectInfoData.setData(subjectInfoDto.getData());
                subjectInfoData.setDate(new Date());

                subjectInfoDataRepo.save(subjectInfoData);

                return messageSource.getMessage("Subject.info", null,LocaleContextHolder.getLocale());
            }
        }else {
            throw new NotFoundException(messageSource.getMessage("NotFound.branch", null, LocaleContextHolder.getLocale()));
        }
    }

    /*
    *
    * Get subject information
    *
    */
    public Set<SubjectInfoViewDto> getSubjectInformation(Long branchId, Long subjectId, int year, int section){
        SubjectInfo subjectInfoExist = subjectInfoRepo.findSubjectInfoUniqueData(branchId, year, section,subjectId);

        if(null != subjectInfoExist){
            Set<SubjectInfoData> subjectInfoData = subjectInfoDataRepo.findByInfoId(subjectInfoExist.getInfoId());

            if(null != subjectInfoData){

                Set<SubjectInfoViewDto> subjectInfoViewDtos = new LinkedHashSet<>();

                for(SubjectInfoData subjectInfoData1 : subjectInfoData){
                    System.out.println("Id is : "+subjectInfoData1.getDataId());
                    SubjectInfoViewDto subjectInfoViewDto = new SubjectInfoViewDto();

                    subjectInfoViewDto.setData(subjectInfoData1.getData());
                    subjectInfoViewDto.setDate(subjectInfoData1.getDate());
                    subjectInfoViewDto.setDataId(subjectInfoData1.getDataId());

                    subjectInfoViewDtos.add(subjectInfoViewDto);
                }
                return subjectInfoViewDtos;

            }else {
                throw new NotFoundException(messageSource.getMessage("NotFound.record", null, LocaleContextHolder.getLocale()));
            }
        }else {
            throw new NotFoundException(messageSource.getMessage("NotFound.record", null, LocaleContextHolder.getLocale()));
        }
    }
}
