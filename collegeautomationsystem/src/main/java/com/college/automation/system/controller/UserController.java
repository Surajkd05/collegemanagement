package com.college.automation.system.controller;

import com.college.automation.system.dtos.AddressDto;
import com.college.automation.system.dtos.StudentProfileDto;
import com.college.automation.system.dtos.UpdateStudentProfileDto;
import com.college.automation.system.dtos.UserProfileDto;
import com.college.automation.system.exceptions.BadRequestException;
import com.college.automation.system.model.Address;
import com.college.automation.system.services.PasswordService;
import com.college.automation.system.services.UserAuthenticationService;
import com.college.automation.system.services.UserService;
import com.college.automation.system.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Autowired
    private UserService userService;


    @Autowired
    private PasswordService passwordService;

    @Autowired
    private PasswordValidator passwordValidator;

    @GetMapping(path = "/")
    public UserProfileDto getUserDetails() {
        String username = userAuthenticationService.getUserName();
        return userService.getUserProfile(username);
    }

    @GetMapping(path = "/user")
    public UserProfileDto getUserDetails(@RequestParam("userId") Long userId) {
        return userService.getUserProfile1(userId);
    }

    @GetMapping(path = "/student")
    public StudentProfileDto getStudentDetails() {
        String username = userAuthenticationService.getUserName();
        return userService.getStudentProfile(username);
    }

    @GetMapping(path = "/address")
    public Set<Address> getCustomerAddress() {
        String username = userAuthenticationService.getUserName();
        return userService.getUserAddress(username);
    }

    @GetMapping(path = "/address1")
    public Set<Address> getCustomerAddress(@RequestParam("userId") Long userId) {
        return userService.getUserAddress(userId);
    }

    @PutMapping(path = "/student")
    public String updateStudent(@Valid @RequestBody UpdateStudentProfileDto userProfileDto) {
        String username = userAuthenticationService.getUserName();
        return userService.updateStudent(userProfileDto, username);
    }

    @PutMapping(path = "/employee")
    public String updateEmployee(@Valid @RequestBody UpdateStudentProfileDto userProfileDto) {
        String username = userAuthenticationService.getUserName();
        return userService.updateEmployee(userProfileDto, username);
    }

    @PatchMapping("/pass")
    public String updatePassword(@RequestParam(value="oldPass") String oldPass, @RequestParam(value="newPass") String newPass, @RequestParam(value="confirmPass") String confirmPass, HttpServletResponse response) {
        String username = userAuthenticationService.getUserName();
        if (passwordValidator.validatePassword(oldPass, newPass, confirmPass)) {
            return passwordService.updatePassword(username, oldPass, newPass, confirmPass);
        } else {
            throw new BadRequestException("Password must be matched or password must be of minimum 8 characters and maximum 15 characters and must contain 1 uppercase letter,1 lowercase letter,1 digit and 1 special character");
        }
    }

    @PostMapping(path = "/")
    public String addAddress(@Valid @RequestBody AddressDto addressDto) {
        String username = userAuthenticationService.getUserName();
        return userService.addAddress(addressDto, username);
    }

    @DeleteMapping(path = "/{addressId}")
    public String deleteAddress(@PathVariable(value = "addressId") Long addressId) {
        return userService.deleteAddress(addressId);
    }

    @PutMapping(path = "/{addressId}")
    public String updateAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable(value = "addressId") Long addressId) {
        String username = userAuthenticationService.getUserName();
        return userService.updateAddress(addressDto, addressId, username);
    }
}
