package com.college.automation.system.controller;

import com.college.automation.system.dtos.SubjectViewDto;
import com.college.automation.system.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    @Autowired
    private SubjectService subjectService;

    /*
    *
    * Get subjects by branch and year
    *
    */
    @GetMapping(path = "/subject")
    public Set<SubjectViewDto> getSubjectsByBranchAndYear(@RequestParam(value = "branchId") Long branchId, @RequestParam(value = "year") int year){
        return subjectService.getSubjectsByBranchAndYear(branchId, year);
    }
}
