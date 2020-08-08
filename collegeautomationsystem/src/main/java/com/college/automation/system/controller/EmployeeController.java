package com.college.automation.system.controller;

import com.college.automation.system.dtos.StudentViewDto;
import com.college.automation.system.dtos.SubjectInfoDto;
import com.college.automation.system.dtos.SubjectInfoViewDto;
import com.college.automation.system.dtos.SubjectViewDto;
import com.college.automation.system.services.AdminService;
import com.college.automation.system.services.StudentService;
import com.college.automation.system.services.SubjectService;
import com.college.automation.system.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    /*
    *
    * Get subjects by branch and year
    *
    */
    @GetMapping(path = "/subject")
    public Set<SubjectViewDto> getSubjectsByBranchAndYear(@RequestParam(value = "branchId") Long branchId, @RequestParam(value = "year") int year){
        return subjectService.getSubjectsByBranchAndYear(branchId, year);
    }



    @PostMapping(path = "/subject")
    public String addSubjectInfo(@RequestBody SubjectInfoDto subjectInfoDto){
        return subjectService.addSubjectInformationSectionWise(subjectInfoDto);
    }

    /*
     *
     *Get subjects by employee id
     *
     */
    @GetMapping(path = "/subject1")
    public Set<SubjectViewDto> getSubjectsByEmployee(){
        String username = userAuthenticationService.getUserName();
        return subjectService.getSubjectsByEmployee(username);
    }

    @GetMapping(path = "/student")
    public Set<StudentViewDto> getAllStudents(@RequestParam(value = "branchId") Long branchId, @RequestParam(value = "year") int year, @RequestParam(value = "section") int section, @RequestParam(value = "semester") int semester){
        return studentService.getAllStudents(branchId, year, section, semester);
    }

    /*
     *
     * Activate Student controller
     *
     */
    @PatchMapping(path = "/activateStudent/{id}")
    public String activateStudent(@PathVariable(value = "id") Long id, HttpServletResponse response){
        System.out.println("User authentication is : "+userAuthenticationService.getUserName());
        String username = userAuthenticationService.getUserName();
        return adminService.activateUser(username,id);
    }

    /*
     *
     * Deactivate student controller
     *
     */
    @PatchMapping(path = "/de-activateStudent/{id}")
    public String deactivateStudent(@PathVariable(value = "id") Long id,HttpServletResponse response){
        String username = userAuthenticationService.getUserName();
        return adminService.deactivateUser(username,id);
    }
}
