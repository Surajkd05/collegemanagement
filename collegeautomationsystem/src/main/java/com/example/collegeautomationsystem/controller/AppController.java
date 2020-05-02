package com.example.collegeautomationsystem.controller;

import com.example.collegeautomationsystem.model.Employee;
import com.example.collegeautomationsystem.services.AppUserDetailsService;
import com.example.collegeautomationsystem.services.PasswordService;
import com.example.collegeautomationsystem.validator.EmailValidation;
import com.example.collegeautomationsystem.validator.PasswordValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/college/app")
public class AppController {

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private EmailValidation emailValidation;

    @Autowired
    private PasswordValidation passwordValidation;

    @GetMapping("/doLogout")
    public String logout(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return "Logged out successfully";
    }

    @GetMapping("getEmployee/{userId}")
    public Employee getEmployee(Long userId){
        return appUserDetailsService.getEmployee(userId);
    }

    @PostMapping("/getToken/{email}")
    public String getToken(@PathVariable("email") String email, HttpServletResponse response){
        if(emailValidation.validateEmail(email)) {
            String message = passwordService.sendToken(email);
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
    public String resetPassword(@RequestParam("token") String token, @RequestParam("email") String email,
                                @RequestParam String oldPass, String newPass, String confirmPass, HttpServletResponse response){
        if(passwordValidation.validatePassword(oldPass,newPass,confirmPass)){
            String message = passwordService.resetPassword(email,token,oldPass,newPass,confirmPass);
            if(!message.equals("Password successfully changed")){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            return message;
        }else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Password must be matched or password must be of minimum 8 characters and maximum 15 characters and must contain 1 uppercase letter,1 lowercase letter,1 digit and 1 special character";
        }
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    /*@PutMapping("/updateUserDetails/{username}")
    public void updateUserUsername(@PathVariable("username") String username, String userName){
        appUserDetailsService.updateUserUsername(username,userName);
    }*/
}