package com.college.automation.system.dtos;

import com.college.automation.system.model.Address;
import com.college.automation.system.model.Role;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Set;

public class EmployeeDto {

    @Column(unique = true)
    private String username;

    @NotEmpty(message = "{NotNull.firstName}")
    private String firstName;

    @NotEmpty(message = "{NotNull.lastName}")
    private String lastName;

    private int age;

    @Temporal(TemporalType.DATE)
    @Past
    private Date dateOfBirth;

    private String gender;

    @NotEmpty(message = "{NotNull.email}")
    @Email(message = "{Email.valid}")
    private String email;

    @NotEmpty(message = "{Password.required}")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%!])(?=.*[A-Z]).{8,15}",message = "{Password.message}")
    private String password;

    @NotEmpty(message = "{Password.required}")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%!])(?=.*[A-Z]).{8,15}",message = "{Password.message}")
    private String confirmPassword;

    @NotEmpty(message = "{NotNull.mobile}")
    @Pattern(regexp="\\d{10}", message="{Mobile.valid}")
    private String mobileNo;

    private Long branchId;

    private boolean isAccountNonLocked;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String  gender) {
        this.gender = gender;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String empMobileNo) {
        this.mobileNo = empMobileNo;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
