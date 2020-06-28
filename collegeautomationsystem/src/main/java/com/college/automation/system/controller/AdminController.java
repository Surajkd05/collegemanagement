package com.college.automation.system.controller;

import com.college.automation.system.dtos.AllocateSubjectDto;
import com.college.automation.system.dtos.BranchDto;
import com.college.automation.system.dtos.SubjectDto;
import com.college.automation.system.services.AdminService;
import com.college.automation.system.services.BranchService;
import com.college.automation.system.services.SubjectService;
import com.college.automation.system.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private SubjectService subjectService;

    /*
     *
     * Get student controller
     *
     */
    @GetMapping(path = "/students")
    public MappingJacksonValue getStudents(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size, @RequestParam(defaultValue = "userId") String SortBy){
        String username = userAuthenticationService.getUserName();
        return adminService.registeredStudents(username,page, size, SortBy);
    }

    /*
     *
     * Get employee controller
     *
     */
    @GetMapping(path = "/employees")
    public MappingJacksonValue getEmployees(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size, @RequestParam(defaultValue = "userId") String SortBy){
        String username = userAuthenticationService.getUserName();
        return adminService.registeredEmployees(username,page, size, SortBy);
    }

    @GetMapping(path = "/employees1")
    public MappingJacksonValue getEmployees1(){
        return adminService.getEmployees();
    }

    /*
     *
     * Activate Student controller
     *
     */
    @PatchMapping(path = "/activateStudent/{id}")
    public String activateStudent(@PathVariable(value = "id") Long id,HttpServletResponse response){
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

    /*
     *
     * Activate employee controller
     *
     */
    @PatchMapping(path = "/activateEmployee/{id}")
    public String activateEmployee(@PathVariable(value = "id") Long id,HttpServletResponse response){
        String username = userAuthenticationService.getUserName();
        return adminService.activateUser(username,id);
    }

    /*
     *
     * Deactivate employee controller
     *
     */
    @PatchMapping(path = "/de-activateEmployee/{id}")
    public String deactivateEmployee(@PathVariable(value = "id") Long id,HttpServletResponse response){
        String username = userAuthenticationService.getUserName();
        return adminService.deactivateUser(username,id);
    }

    /*
    *
    * Add branch controller
    *
     */
    @PostMapping(path="/branch")
    public String addBranch(@RequestBody BranchDto branchDto){
        String username = userAuthenticationService.getUserName();
        return branchService.addBranch(username,branchDto);
    }

    /*
    *
    * Add subjects by branch and year
    *
    */
    @PostMapping(path="/subject")
    public String addSubjectByBranch(@RequestBody SubjectDto subjectDto){
        return subjectService.addSubjectByBranchAndYear(subjectDto);
    }

    /*
    *
    * Allocate subject to employee
    *
    */
    @PostMapping(path="/allocate")
    public String allocateEmployeeSubject(@RequestBody AllocateSubjectDto allocateSubjectDto){
        return subjectService.addEmployeeSubject(allocateSubjectDto);
    }
}

