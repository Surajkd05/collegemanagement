package com.college.automation.system.controller;

import com.college.automation.system.dtos.EmployeeDto;
import com.college.automation.system.dtos.StudentDto;
import com.college.automation.system.model.Admin;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.UserRepo;
import com.college.automation.system.services.AppUserDetailsService;
import com.college.automation.system.services.DtoService;
import com.college.automation.system.services.SendEmail;
import com.college.automation.system.services.UserActivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Autowired
    private UserActivateService userActivateService;

    @Autowired
    SendEmail sendEmail;

    @Autowired
    private DtoService dtoService;

    /*@Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }*/

    @PostMapping(path = "/student")
    public String registerStudent(@Valid @RequestBody StudentDto studentDto, HttpServletResponse response) {
        System.out.println("Received data is"+studentDto);
        if (dtoService.validateStudent(studentDto).equals("validated")) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return appUserDetailsService.registerStudent(studentDto);

        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return dtoService.validateStudent(studentDto);
        }
    }

    @PostMapping(path = "/employee")
    public String registerEmployee(@Valid @RequestBody EmployeeDto employeeDto, HttpServletResponse response) {
        if (dtoService.validateEmployee(employeeDto).equals("validated")) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return appUserDetailsService.registerEmployee(employeeDto);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return dtoService.validateEmployee(employeeDto);
        }
    }

    @PutMapping(path = "/confirm-account")
    public String confirmCustomerAccount(@RequestParam("token") String token, HttpServletResponse response) {
        String message = userActivateService.activateUser(token);
        if (!message.equals("Successfully activated")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PostMapping(path = "/resend-activation")
    public String resendLink(@RequestParam("email") String email, HttpServletResponse response) {
        String message = userActivateService.resendLink(email);
        if (!message.equals("Successful")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PostMapping(path = "/admin")
    public User registerAdmin(@Valid @RequestBody Admin admin) {

        User user = appUserDetailsService.registerAdmin(admin);

        return user;
    }
}