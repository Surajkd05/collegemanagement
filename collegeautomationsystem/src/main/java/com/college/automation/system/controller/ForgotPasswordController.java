package com.college.automation.system.controller;

import com.college.automation.system.services.ForgotPasswordService;
import com.college.automation.system.validator.EmailValidation;
import com.college.automation.system.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/forgotPassword")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private EmailValidation emailValidation;

    @Autowired
    private PasswordValidator passwordValidator;

    @PostMapping(path = "/token/{email}")
    public String sendToken(@PathVariable("email") String email, HttpServletResponse response){
        if (emailValidation.validateEmail(email)){
            String message = forgotPasswordService.sendToken(email);
            if(!message.equals("Change your password")){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            return message;
        }else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Email is not valid";
        }
    }

    @PatchMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token, @RequestParam("email") String email, @RequestParam String pass, @RequestParam String cpass, HttpServletResponse response) {
        if(passwordValidator.validatePassword(pass,cpass)){
            String message = forgotPasswordService.resetPassword(email, token, pass, cpass);
            if(!message.equals("Password successfully changed")){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            return message;
        }else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Password must be matched or password must be of minimum 8 characters and maximum 15 characters and must contain 1 uppercase letter,1 lowercase letter,1 digit and 1 special character";
        }
    }
}
