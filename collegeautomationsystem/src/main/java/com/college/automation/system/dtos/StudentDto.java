package com.college.automation.system.dtos;

import com.college.automation.system.model.Address;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class StudentDto {

    @Column(unique = true)
    private String username;

    @NotEmpty(message = "{NotNull.firstName}")
    private String firstName;

    @NotEmpty(message = "{NotNull.lastName}")
    private String lastName;

    private int age;

    private Long branchId;

    @Temporal(TemporalType.DATE)
    @Past
    private Date dateOfBirth;

    private String gender;

    //private Address address;

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

    private int year;

    private int section;

    private int semester;

    //private Set<Role> roles;

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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
