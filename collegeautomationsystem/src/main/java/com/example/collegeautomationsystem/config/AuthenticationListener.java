package com.example.collegeautomationsystem.config;

import com.example.collegeautomationsystem.model.AppUser;
import com.example.collegeautomationsystem.services.UserAttemptService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.event.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl.process;

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
