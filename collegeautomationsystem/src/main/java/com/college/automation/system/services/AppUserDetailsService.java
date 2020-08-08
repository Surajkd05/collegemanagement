package com.college.automation.system.services;

import com.college.automation.system.dao.UserDao;
import com.college.automation.system.dtos.EmployeeDto;
import com.college.automation.system.dtos.StudentDto;
import com.college.automation.system.model.*;
import com.college.automation.system.repos.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDao userDao;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private UserActivateRepo userActivateRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private MessageSource messageSource;

    public Employee getEmployee(Long userId){
        return employeeRepo.findByUserId(userId);
    }

    public String registerStudent(StudentDto studentDto){
        Student student = new Student();
        BeanUtils.copyProperties(studentDto, student);

        Optional<Branches> branches = branchRepo.findById(studentDto.getBranchId());

        student.setBranches(branches.get());
        String pass = passwordEncoder.encode(student.getPassword());
        student.setPassword(pass);

        Set<Role> roles = new LinkedHashSet<>();
        roles.add(new Role("STUDENT"));
        student.setPassword(pass);
        student.setRoles(roles);
        student.setAccountNonLocked(true);

        studentRepo.save(student);

        String token = UUID.randomUUID().toString();

        VerificationToken studentActivate = new VerificationToken();
        studentActivate.setToken(token);
        studentActivate.setUserEmail(student.getEmail());
        studentActivate.setGeneratedDate(new Date());

        userActivateRepo.save(studentActivate);

        String  email = student.getEmail();

        sendEmail.sendEmail(messageSource.getMessage("Account.activation.token", null, LocaleContextHolder.getLocale()),messageSource.getMessage("Account.confirm", null, LocaleContextHolder.getLocale())+token,email);

        return messageSource.getMessage("Registration.successful", null, LocaleContextHolder.getLocale());
    }

    public String registerEmployee(EmployeeDto employeeDto){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto,employee);

        if(null != employeeDto.getBranchId() ) {
            Optional<Branches> branches = branchRepo.findById(employeeDto.getBranchId());
            employee.setBranches(branches.get());
        }

        String pass = passwordEncoder.encode(employee.getPassword());
        Set<Role> roles = new LinkedHashSet<>();
        roles.add(new Role("EMPLOYEE"));
        employee.setRoles(roles);
        employee.setPassword(pass);
        employee.setAccountNonLocked(true);
        employeeRepo.save(employee);

        String token = UUID.randomUUID().toString();

        VerificationToken employeeActivate = new VerificationToken();
        employeeActivate .setToken(token);
        employeeActivate .setUserEmail(employee.getEmail());
        employeeActivate .setGeneratedDate(new Date());

        userActivateRepo.save(employeeActivate );

        String  email = employee.getEmail();

        sendEmail.sendEmail(messageSource.getMessage("Account.activation.token", null, LocaleContextHolder.getLocale()),messageSource.getMessage("Account.confirm", null, LocaleContextHolder.getLocale())+token,email);

        return messageSource.getMessage("Registration.successful", null, LocaleContextHolder.getLocale());
    }

    //public Student findByStudentId

    public User registerAdmin(Admin admin)
    {
        String pass = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(pass);
        Set<Role> roles = new LinkedHashSet<>();
        roles.add(new Role("ADMIN"));
        admin.setRoles(roles);
        return adminRepo.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userDao.loadUserByUsername(username);
        return userDetails;
    }
}
