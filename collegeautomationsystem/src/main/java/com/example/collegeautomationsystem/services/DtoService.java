package com.example.collegeautomationsystem.services;

import com.example.collegeautomationsystem.dtos.EmployeeDto;
import com.example.collegeautomationsystem.dtos.StudentDto;
import com.example.collegeautomationsystem.model.User;
import com.example.collegeautomationsystem.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DtoService {

    @Autowired
    private UserRepository userRepository;

    public String validateStudent(StudentDto studentDto){
        String str = "validated";
        User user = userRepository.findByEmail(studentDto.getEmail());
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
        User user = userRepository.findByEmail(employeeDto.getEmail());
        if (null!=user){
            str = "Email already exist";
        }else if(!employeeDto.getPassword().equals(employeeDto.getConfirmPassword())){
            str = "Passwords not matched";
        }
        return str;
    }
}
