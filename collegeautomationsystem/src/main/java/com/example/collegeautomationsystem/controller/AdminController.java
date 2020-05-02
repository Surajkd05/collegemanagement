package com.example.collegeautomationsystem.controller;

import com.example.collegeautomationsystem.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/college/admin/home")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping(path = "/students")
    public MappingJacksonValue getStudents(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size, @RequestParam(defaultValue = "userId") String SortBy){
        return adminService.registeredStudents(page, size, SortBy);
    }

    @GetMapping(path = "/employees")
    public MappingJacksonValue getEmployees(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size, @RequestParam(defaultValue = "userId") String SortBy){
        return adminService.registeredEmployees(page, size, SortBy);
    }

    @PatchMapping(path = "/activateStudent/{id}")
    public String activateStudent(@PathVariable(value = "id") Long id,HttpServletResponse response){
        String message = adminService.activateUser(id);

        if(!message.equals("Account activated")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PatchMapping(path = "/de-activateStudent/{id}")
    public String deactivateStudent(@PathVariable(value = "id") Long id,HttpServletResponse response){
        String message = adminService.deactivateUser(id);
        if(!message.equals("Account de-activated")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PatchMapping(path = "/activateEmployee/{id}")
    public String activateEmployee(@PathVariable(value = "id") Long id,HttpServletResponse response){
        String message = adminService.activateUser(id);

        if(!message.equals("Account activated")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PatchMapping(path = "/de-activateEmployee/{id}")
    public String deactivateEmployee(@PathVariable(value = "id") Long id,HttpServletResponse response){
        String message = adminService.deactivateUser(id);
        if(!message.equals("Account de-activated")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }
}

