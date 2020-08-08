package com.college.automation.system.validator;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    private static final String pattern = "((?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%!]).{8,15})";

    public boolean validatePassword(String pass, String cpass ) {
        if (pass.equals(cpass)){
            if (pass.matches(pattern)) {
                if (cpass.matches(pattern)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validatePassword(String oldPass, String pass, String cpass) {
        if (oldPass.matches(pattern)) {
            if (pass.equals(cpass)) {
                if (pass.matches(pattern)) {
                    return cpass.matches(pattern);
                }
            }
        }
        return false;
    }
}
