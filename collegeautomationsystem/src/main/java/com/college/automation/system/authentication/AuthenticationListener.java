package com.college.automation.system.authentication;

import com.college.automation.system.services.UserAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.*;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;

@Component
public class AuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent> {

    @Autowired
    private UserAttemptService userAttemptService;

    @Override
    @Transactional
    public void onApplicationEvent(AbstractAuthenticationEvent appEvent) {

        if (appEvent instanceof AuthenticationSuccessEvent)
        {
            AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent;

            //String username = event.getAuthentication().getName();

            String username = new String();
            LinkedHashMap<String,String> userMap = new LinkedHashMap<>();
            try {
                userMap = (LinkedHashMap<String, String>) event.getAuthentication().getDetails();
            } catch (ClassCastException ex) {
            }
            try {
                username = userMap.get("username");
            } catch (NullPointerException e) {
            }
            userAttemptService.userSuccessAttempt(username);
        }

        if (appEvent instanceof AuthenticationFailureBadCredentialsEvent)
        {
            AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent) appEvent;

            LinkedHashMap<String ,String> usermap = new LinkedHashMap<>();
            usermap = (LinkedHashMap<String, String>) event.getAuthentication().getDetails();

            userAttemptService.userFailedAttempt((String) usermap.get("username"));
        }
    }
}
