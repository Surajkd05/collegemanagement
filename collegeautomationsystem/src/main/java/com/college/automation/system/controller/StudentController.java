package com.college.automation.system.controller;

import com.college.automation.system.dtos.PlacementDto;
import com.college.automation.system.dtos.SubjectInfoDto;
import com.college.automation.system.dtos.SubjectInfoViewDto;
import com.college.automation.system.dtos.SubjectViewDto;
import com.college.automation.system.model.Schedule;
import com.college.automation.system.model.Student;
import com.college.automation.system.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PlacementService placementService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @GetMapping(path="/studentList")
    private List<Student> getStudentsForAttendance(@RequestParam String branch, @RequestParam String year, @RequestParam String section){
            return studentService.getStudentsForAttendance(branch, year, section);
    }

    @GetMapping(path = "/getSchedule")
    public List<Schedule> getSchedule(@RequestParam String branch, @RequestParam String year, @RequestParam String section){
        return scheduleService.findSchedule(branch, year, section);
    }

    @GetMapping(path = "/info")
    public Set<SubjectInfoViewDto> getSubjectInfo(@RequestParam(value = "branchId") Long branchId, @RequestParam(value = "subjectId") Long subjectId, @RequestParam(value = "year") int year, @RequestParam(value = "section") int section){
        return subjectService.getSubjectInformation(branchId, subjectId, year, section);
    }

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
