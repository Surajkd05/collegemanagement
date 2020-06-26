package com.college.automation.system.services;

import com.college.automation.system.dao.UserDao;
import com.college.automation.system.dtos.EmployeeDto;
import com.college.automation.system.dtos.StudentDto;
import com.college.automation.system.model.*;
import com.college.automation.system.repos.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    public Employee getEmployee(Long userId){
        return employeeRepo.findByUserId(userId);
    }

    public String registerStudent(StudentDto studentDto){
        Student student = new Student();
        BeanUtils.copyProperties(studentDto, student);
        String pass = passwordEncoder.encode(student.getPassword());
        student.setPassword(pass);

        Set<Role> roles = new HashSet<>();
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

        sendEmail.sendEmail("ACCOUNT ACTIVATE TOKEN","To confirm your account, please click here : "
                +"http://localhost:8080/college/register/confirm-account?token="+token,email);

        return "Registration Successful";
    }

    public String registerEmployee(EmployeeDto employeeDto){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto,employee);
        String pass = passwordEncoder.encode(employee.getPassword());
        Set<Role> roles = new HashSet<>();
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

        sendEmail.sendEmail("ACCOUNT ACTIVATE TOKEN","To confirm your account, please click here : "
                +"http://localhost:8080/college/register/confirm-account?token="+token,email);

        return "Registration Successful";
    }

    //public Student findByStudentId

    public User registerAdmin(Admin admin)
    {
        String pass = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(pass);
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ADMIN"));
        admin.setRoles(roles);
        return adminRepo.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userDao.loadUserByUsername(username);
        return userDetails;
    }

    @Transactional
    @Modifying
    public void updateUserUsername(String username,String userName){
        Long userId = userRepo.findUserId(username);
        userRepo.updateUserUsername(userId,userName);
    }

    @Transactional
    @Modifying
    public void updateUserFirstName(String username,String firstName){
        Long userId = userRepo.findUserId(username);
        userRepo.updateUserFirstName(userId,firstName);
    }

    @Transactional
    @Modifying
    public void updateUserLastName(String username,String lastName){
        Long userId = userRepo.findUserId(username);
        userRepo.updateUserLastName(userId,lastName);
    }

    @Transactional
    @Modifying
    public void updateUserEmail(String username,String email){
        Long userId = userRepo.findUserId(username);
        userRepo.updateUserEmail(userId,email);
    }

    @Transactional
    @Modifying
    public void updateUserPassword(String username,String password){
        Long userId = userRepo.findUserId(username);
        userRepo.updateUserPassword(userId,password);
    }
}
