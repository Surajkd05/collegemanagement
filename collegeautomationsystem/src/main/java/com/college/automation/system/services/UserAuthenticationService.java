package com.college.automation.system.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService {
    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        UserDetails userDetail = (UserDetails) authentication.getPrincipal();

        return userDetail.getUsername();
    }
}
