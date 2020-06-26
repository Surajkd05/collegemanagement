package com.college.automation.system.services;

import com.college.automation.system.dtos.EmployeeDto;
import com.college.automation.system.dtos.StudentDto;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DtoService {

    @Autowired
    private UserRepo userRepo;

    public String validateStudent(StudentDto studentDto){
        String str = "validated";
        User user = userRepo.findByEmail(studentDto.getEmail());
        System.out.println("user object "+user);
        if (null!=user){
           str = "Email already exist";
        }else if(!studentDto.getPassword().equals(studentDto.getConfirmPassword())){
            str="Passswords not matched";
        }
        return str;
    }

    public String validateEmployee(EmployeeDto employeeDto){
        String str = "validated";
        User user = userRepo.findByEmail(employeeDto.getEmail());
        if (null!=user){
            str = "Email already exist";
        }else if(!employeeDto.getPassword().equals(employeeDto.getConfirmPassword())){
            str = "Passwords not matched";
        }
        return str;
    }
}
