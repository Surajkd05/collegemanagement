package com.college.automation.system.controller;

import com.college.automation.system.dtos.BranchDto;
import com.college.automation.system.services.AdminService;
import com.college.automation.system.services.BranchService;
import com.college.automation.system.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/admin/home")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Autowired
    private BranchService branchService;

    /*
     *
     * Get student controller
     *
     */
    @GetMapping(path = "/students")
    public MappingJacksonValue getStudents(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size, @RequestParam(defaultValue = "userId") String SortBy){
        return adminService.registeredStudents(page, size, SortBy);
    }

    /*
     *
     * Get employee controller
     *
     */
    @GetMapping(path = "/employees")
    public MappingJacksonValue getEmployees(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size, @RequestParam(defaultValue = "userId") String SortBy){
        return adminService.registeredEmployees(page, size, SortBy);
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
        String message = adminService.activateUser(id);

        if(!message.equals("Account activated")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    /*
     *
     * Deactivate student controller
     *
     */
    @PatchMapping(path = "/de-activateStudent/{id}")
    public String deactivateStudent(@PathVariable(value = "id") Long id,HttpServletResponse response){
        String message = adminService.deactivateUser(id);
        if(!message.equals("Account de-activated")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    /*
     *
     * Activate employee controller
     *
     */
    @PatchMapping(path = "/activateEmployee/{id}")
    public String activateEmployee(@PathVariable(value = "id") Long id,HttpServletResponse response){
        String message = adminService.activateUser(id);

        if(!message.equals("Account activated")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    /*
     *
     * Deactivate employee controller
     *
     */
    @PatchMapping(path = "/de-activateEmployee/{id}")
    public String deactivateEmployee(@PathVariable(value = "id") Long id,HttpServletResponse response){
        String message = adminService.deactivateUser(id);
        if(!message.equals("Account de-activated")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    /*
    *
    * Add branch controller
    *
     */
    @PostMapping(path="/branch")
    public String addBranch(@RequestBody BranchDto branchDto){
        return branchService.addBranch(branchDto);
    }
}

