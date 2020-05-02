package com.example.collegeautomationsystem.services;

import com.example.collegeautomationsystem.exception.UserNotFoundException;
import com.example.collegeautomationsystem.model.Employee;
import com.example.collegeautomationsystem.model.Student;
import com.example.collegeautomationsystem.model.User;
import com.example.collegeautomationsystem.repos.EmployeeRepository;
import com.example.collegeautomationsystem.repos.StudentRepository;
import com.example.collegeautomationsystem.repos.UserRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SendEmail sendEmail;

    public MappingJacksonValue registeredStudents(String page, String size, String SortBy){
        List<Student> students = studentRepository.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(SortBy))).getContent();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("userId","firstName","lastName","email","active");
        FilterProvider filterProvider =  new SimpleFilterProvider().addFilter("Student-Filter",filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(students);

        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    public MappingJacksonValue registeredEmployees(String page, String size, String SortBy){
        List<Employee> sellers = employeeRepository.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(SortBy))).getContent();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("userId","firstName","lastName","email","active");
        FilterProvider filterProvider =  new SimpleFilterProvider().addFilter("Employee-Filter",filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(sellers);

        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    public String activateUser(Long userId){
        Optional<User> user = userRepository.findById(userId);
        StringBuilder sb = new StringBuilder();

        if(user.isPresent()){
            boolean flag = user.get().isActive();
            if(!flag){
                user.get().setActive(true);
                userRepository.save(user.get());
                sendEmail.sendEmail("Account Activation","Your account is successfully activated",
                        user.get().getEmail());
                sb.append("Account activated");
            }else {
                sb.append("User is already activated");
            }
        }else {
            throw new UserNotFoundException("User not found");
        }
        return sb.toString();
    }

    public String deactivateUser(Long userId){
        Optional<User> user = userRepository.findById(userId);
        StringBuilder sb = new StringBuilder();

        if(user.isPresent()){
            boolean flag = user.get().isActive();
            if(flag){
                user.get().setActive(false);
                userRepository.save(user.get());
                sendEmail.sendEmail("Account De-Activation","Your account is successfully de-activated",
                        user.get().getEmail());
                sb.append("Account de-activated");
            }else {
                sb.append("User is already de-activated");
            }
        }else {
            throw new UserNotFoundException("User not found");
        }
        return sb.toString();
    }
}
