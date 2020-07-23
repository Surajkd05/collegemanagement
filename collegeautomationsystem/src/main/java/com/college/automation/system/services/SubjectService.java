package com.college.automation.system.services;

import com.college.automation.system.dtos.*;
import com.college.automation.system.exceptions.BadRequestException;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.*;
import com.college.automation.system.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
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

            return "Subject added successfully";
        }
        return null;
    }

    /*
    *
    * Get Subjects
    *
    */
    public Set<SubjectViewDto> getSubjectsByBranchAndYear(Long branchId, int year){

        System.out.println("Branch id is : "+branchId + "and year is : "+year);
        Set<Subject> subjectSet = subjectRepo.findSubjectByBranchAndYear(branchId,year);

        if(!subjectSet.isEmpty()){
            Set<SubjectViewDto> subjectViewDtos = new HashSet<>();

            for (Subject subject : subjectSet){
                SubjectViewDto subjectViewDto = new SubjectViewDto();

                subjectViewDto.setId(subject.getSubjectId());
                subjectViewDto.setSubjectCode(subject.getSubjectCode());
                subjectViewDto.setSubjectName(subject.getSubjectName());

                subjectViewDtos.add(subjectViewDto);
            }

            return  subjectViewDtos;
        }else {
            throw new NotFoundException("No subjects found");
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
                Set<Subject> subjects = new HashSet<>();
                subjects.add(subject.get());
                employee.setSubjects(subjects);
                employeeRepo.save(employee);
                return "Subject allocated";
            }else {
                throw new NotFoundException("Subject not exist");
            }
        }else {
            throw new BadRequestException("Employee not exist");
        }
    }

    /*
    *
    *Get subjects by employeeId
    *
    */
    public Set<SubjectViewDto> getSubjectsByEmployee(String username){

        Employee employee = employeeRepo.findByEmail(username);

        Set<SubjectViewDto> subjectViewDtos = new HashSet<>();
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
            throw new NotFoundException("Subject is not allocated to employee");
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

                return "Subject info added successfully";
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

                return "Subject info successfully added";
            }
        }else {
            throw new NotFoundException("Branch not exist");
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

                Set<SubjectInfoViewDto> subjectInfoViewDtos = new HashSet<>();

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
                throw new NotFoundException("No data found");
            }
        }else {
            throw new NotFoundException("No data found");
        }
    }
}
