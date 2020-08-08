package com.college.automation.system.services;

import com.college.automation.system.exceptions.BadRequestException;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.*;
import com.college.automation.system.repos.BranchRepo;
import com.college.automation.system.repos.EmployeeRepo;
import com.college.automation.system.repos.StudentRepo;
import com.college.automation.system.repos.UserRepo;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private MessageSource messageSource;

    public MappingJacksonValue registeredStudents(String username, String page, String size, String SortBy) {
        User admin = userRepo.findByEmail(username);

        if (null != admin) {
            List<Student> students = studentRepo.findAll(PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), Sort.by(SortBy))).getContent();
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("userId", "firstName", "lastName", "email", "active");
            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("Student-Filter", filter);

            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(students);

            mappingJacksonValue.setFilters(filterProvider);

            return mappingJacksonValue;
        } else {
            throw new BadRequestException(messageSource.getMessage("Not.authorized", null, LocaleContextHolder.getLocale()));
        }
    }

    public MappingJacksonValue registeredEmployees(String username, String page, String size, String SortBy) {
        User admin = userRepo.findByEmail(username);

        if (null != admin) {
            List<Employee> sellers = employeeRepo.findAll(PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), Sort.by(SortBy))).getContent();
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("userId", "firstName", "lastName", "email", "active");
            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("Employee-Filter", filter);

            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(sellers);

            mappingJacksonValue.setFilters(filterProvider);
            return mappingJacksonValue;
        } else {
            throw new BadRequestException(messageSource.getMessage("Not.authorized", null, LocaleContextHolder.getLocale()));
        }
    }

    public MappingJacksonValue getEmployees() {
        List<Employee> sellers = employeeRepo.findAll();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("userId", "firstName", "lastName", "email", "active");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("Employee-Filter", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(sellers);

        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    public String activateUser(String username,Long userId) {
        User admin = userRepo.findByEmail(username);

        if(null != admin){
            Optional<User> user = userRepo.findById(userId);
            StringBuilder sb = new StringBuilder();

            if (user.isPresent()) {
                boolean flag = user.get().isActive();
                if (!flag) {
                    user.get().setActive(true);
                    userRepo.save(user.get());
                    sendEmail.sendEmail(messageSource.getMessage("Account.activation", null, LocaleContextHolder.getLocale()), messageSource.getMessage("Account.activated", null, LocaleContextHolder.getLocale()),
                            user.get().getEmail());
                    sb.append(messageSource.getMessage("Account.activated", null, LocaleContextHolder.getLocale()));
                } else {
                    sb.append(messageSource.getMessage("Account.already.activated", null, LocaleContextHolder.getLocale()));
                }
            } else {
                throw new NotFoundException(messageSource.getMessage("NotFound.user", null, LocaleContextHolder.getLocale()));
            }
            return sb.toString();
        }else {
            throw new BadRequestException(messageSource.getMessage("Not.authorized", null, LocaleContextHolder.getLocale()));
        }
    }

    public String deactivateUser(String username,Long userId) {
        User admin = userRepo.findByEmail(username);
        if(null != admin){
            Optional<User> user = userRepo.findById(userId);
            StringBuilder sb = new StringBuilder();

            if (user.isPresent()) {
                boolean flag = user.get().isActive();
                if (flag) {
                    user.get().setActive(false);
                    userRepo.save(user.get());
                    sendEmail.sendEmail(messageSource.getMessage("Account.DeActivation", null, LocaleContextHolder.getLocale()), messageSource.getMessage("Account.deactivated", null, LocaleContextHolder.getLocale()),
                            user.get().getEmail());
                    sb.append(messageSource.getMessage("Account.deactivated", null, LocaleContextHolder.getLocale()));
                } else {
                    sb.append(messageSource.getMessage("Account.already.deactivated", null, LocaleContextHolder.getLocale()));
                }
            } else {
                throw new NotFoundException(messageSource.getMessage("NotFound.user", null, LocaleContextHolder.getLocale()));
            }
            return sb.toString();
        }else {
            throw new BadRequestException(messageSource.getMessage("Not.authorized", null, LocaleContextHolder.getLocale()));
        }
    }

    public Set<Employee> getEmployeeByBranch(Long branchId){

        Optional<Branches> branches = branchRepo.findById(branchId);
        List<Employee> employees = new ArrayList<>();
        try{
            employees = employeeRepo.findByBranches(branches.get());
        }catch (Exception e){
            e.printStackTrace();
        }
        Set<Employee> employeeSet = new LinkedHashSet<>();
        for(Employee employee : employees){
                employeeSet.add(employee);
        }
        return employeeSet;
    }
}
