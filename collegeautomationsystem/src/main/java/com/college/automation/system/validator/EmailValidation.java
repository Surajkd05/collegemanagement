package com.college.automation.system.validator;

import org.springframework.stereotype.Component;

@Component
public class EmailValidation {

    private static final String pattern = "^[A-Za-z0-9+_.-]+@(.+)$";

    public boolean validateEmail(String email ) {
        return email.matches(pattern);
    }
}
