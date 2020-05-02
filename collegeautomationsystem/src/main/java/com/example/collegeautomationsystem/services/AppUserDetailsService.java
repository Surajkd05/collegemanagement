package com.example.collegeautomationsystem.services;

import com.example.collegeautomationsystem.dao.UserDao;
import com.example.collegeautomationsystem.dtos.EmployeeDto;
import com.example.collegeautomationsystem.dtos.StudentDto;
import com.example.collegeautomationsystem.model.*;
import com.example.collegeautomationsystem.repos.*;
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
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserActivateRepo userActivateRepo;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SendEmail sendEmail;

    public Employee getEmployee(Long userId){
        return employeeRepository.findByUserId(userId);
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

        studentRepository.save(student);

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
        employee.setPassword(pass);
        employeeRepository.save(employee);

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
        return adminRepository.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userDao.loadUserByUsername(username);
        return userDetails;
    }

    @Transactional
    @Modifying
    public void updateUserUsername(String username,String userName){
        Long userId = userRepository.findUserId(username);
        userRepository.updateUserUsername(userId,userName);
    }

    @Transactional
    @Modifying
    public void updateUserFirstName(String username,String firstName){
        Long userId = userRepository.findUserId(username);
        userRepository.updateUserFirstName(userId,firstName);
    }

    @Transactional
    @Modifying
    public void updateUserLastName(String username,String lastName){
        Long userId = userRepository.findUserId(username);
        userRepository.updateUserLastName(userId,lastName);
    }

    @Transactional
    @Modifying
    public void updateUserEmail(String username,String email){
        Long userId = userRepository.findUserId(username);
        userRepository.updateUserEmail(userId,email);
    }

    @Transactional
    @Modifying
    public void updateUserPassword(String username,String password){
        Long userId = userRepository.findUserId(username);
        userRepository.updateUserPassword(userId,password);
    }
}
