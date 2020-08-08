package com.college.automation.system.services;

import com.college.automation.system.dtos.EmployeeDto;
import com.college.automation.system.dtos.StudentDto;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class DtoService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MessageSource messageSource;

    public String validateStudent(StudentDto studentDto){
        String str = "validated";
        User user = userRepo.findByEmail(studentDto.getEmail());

        if (null!=user){
           str = messageSource.getMessage("Email.exist", null, LocaleContextHolder.getLocale());
        }else if(!studentDto.getPassword().equals(studentDto.getConfirmPassword())){
            str= messageSource.getMessage("Password.NotMatched", null, LocaleContextHolder.getLocale());
        }
        return str;
    }

    public String validateEmployee(EmployeeDto employeeDto){
        String str = "validated";
        User user = userRepo.findByEmail(employeeDto.getEmail());
        if (null!=user){
            str = messageSource.getMessage("Email.exist", null, LocaleContextHolder.getLocale());
        }else if(!employeeDto.getPassword().equals(employeeDto.getConfirmPassword())){
            str = messageSource.getMessage("Password.NotMatched", null, LocaleContextHolder.getLocale());
        }
        return str;
    }
}
