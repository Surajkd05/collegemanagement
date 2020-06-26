package com.college.automation.system.controller;

import com.college.automation.system.model.Employee;
import com.college.automation.system.services.AppUserDetailsService;
import com.college.automation.system.services.PasswordService;
import com.college.automation.system.validator.EmailValidation;
import com.college.automation.system.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private EmailValidation emailValidation;

    @Autowired
    private PasswordValidator passwordValidator;

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

    @GetMapping("/")
    public String index(){
        return "index";
    }

    /*@PutMapping("/updateUserDetails/{username}")
    public void updateUserUsername(@PathVariable("username") String username, String userName){
        appUserDetailsService.updateUserUsername(username,userName);
    }*/
}