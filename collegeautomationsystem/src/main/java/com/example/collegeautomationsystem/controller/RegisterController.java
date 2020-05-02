package com.example.collegeautomationsystem.controller;

import com.example.collegeautomationsystem.dtos.EmployeeDto;
import com.example.collegeautomationsystem.dtos.StudentDto;
import com.example.collegeautomationsystem.model.Admin;
import com.example.collegeautomationsystem.model.User;
import com.example.collegeautomationsystem.services.AppUserDetailsService;
import com.example.collegeautomationsystem.services.DtoService;
import com.example.collegeautomationsystem.services.SendEmail;
import com.example.collegeautomationsystem.services.UserActivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/college/register")
public class RegisterController {

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Autowired
    private UserActivateService userActivateService;

    @Autowired
    SendEmail sendEmail;

    @Autowired
    private DtoService dtoService;

    @PostMapping(path = "/student")
    public String registerStudent(@Valid @RequestBody StudentDto studentDto, HttpServletResponse response){
        if(dtoService.validateStudent(studentDto).equals("validated")) {
             response.setStatus(HttpServletResponse.SC_CREATED);
             return appUserDetailsService.registerStudent(studentDto);

        }else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return dtoService.validateStudent(studentDto);
        }
    }

    @PostMapping(path = "/employee")
    public String registerEmployee(@Valid @RequestBody EmployeeDto employeeDto, HttpServletResponse response){
        if(dtoService.validateEmployee(employeeDto).equals("validated")) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return appUserDetailsService.registerEmployee(employeeDto);
        }else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return dtoService.validateEmployee(employeeDto);
        }
    }

    @PutMapping(path = "/confirm-account")
    public String confirmCustomerAccount(@RequestParam("token") String token, HttpServletResponse response){
        String message = userActivateService.activateUser(token);
        if(!message.equals("Successfully activated")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PostMapping(path = "/resend-activation")
    public String resendLink(@RequestParam("email") String email, HttpServletResponse response){
        String message = userActivateService.resendLink(email);
        if(!message.equals("Successful")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PostMapping(path = "/admin")
    public User registerAdmin(@Valid @RequestBody Admin admin){

        User user = appUserDetailsService.registerAdmin(admin);

        return user;
    }
}
