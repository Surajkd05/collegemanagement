package com.college.automation.system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserAccountLockedExcetion extends RuntimeException {
    public UserAccountLockedExcetion(String message) {
        super(message);
    }
}
