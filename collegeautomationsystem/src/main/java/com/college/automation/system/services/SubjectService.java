package com.college.automation.system.services;

import com.college.automation.system.dtos.AllocateSubjectDto;
import com.college.automation.system.dtos.SubjectDto;
import com.college.automation.system.dtos.SubjectViewDto;
import com.college.automation.system.exceptions.BadRequestException;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.Branches;
import com.college.automation.system.model.Employee;
import com.college.automation.system.model.Subject;
import com.college.automation.system.repos.BranchRepo;
import com.college.automation.system.repos.EmployeeRepo;
import com.college.automation.system.repos.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
