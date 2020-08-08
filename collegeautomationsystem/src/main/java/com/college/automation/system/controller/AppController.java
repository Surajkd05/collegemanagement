package com.college.automation.system.controller;

import com.college.automation.system.dtos.BranchViewDto;
import com.college.automation.system.dtos.CourseViewDto;
import com.college.automation.system.model.Employee;
import com.college.automation.system.services.AppUserDetailsService;
import com.college.automation.system.services.BranchService;
import com.college.automation.system.services.CourseService;
import com.college.automation.system.services.PasswordService;
import com.college.automation.system.validator.EmailValidation;
import com.college.automation.system.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;


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

    @Autowired
    private CourseService courseService;

    @Autowired
    private BranchService branchService;

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
    
     @GetMapping("/course")
    public Set<CourseViewDto> getAllCourse(){
        return courseService.getCourse();
    }

    /*
     *
     * Get branches controller
     *
     */
    @GetMapping(path = "/branch")
    public Set<BranchViewDto> getBranches(@RequestParam(value = "courseId") Long courseId){
        return branchService.getBranch(courseId);
    }

    /*@PutMapping("/updateUserDetails/{username}")
    public void updateUserUsername(@PathVariable("username") String username, String userName){
        appUserDetailsService.updateUserUsername(username,userName);
    }*/
}