package com.college.automation.system.services;

import com.college.automation.system.model.User;
import com.college.automation.system.model.UserAttempts;
import com.college.automation.system.repos.UserAttemptRepo;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Locale;

@Service
public class UserAttemptService {

    public static final int MAX_ATTEMPT = 2;

    @Autowired
    private UserAttemptRepo userAttemptRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    private MessageSource messageSource;

    public void  userFailedAttempt(String username){

        UserAttempts userAttempt= userAttemptRepo.findByUsername(username);
        User user = userRepo.findByEmail(username);

        if (null != user){
            if(null != userAttempt){
                int attempt= userAttempt.getAttempts();
                if(attempt >= MAX_ATTEMPT){
                    userAttempt.setAttempts(attempt+1);
                    userAttempt.setLastModified(new Date());
                    userAttemptRepo.save(userAttempt);

                    user.setAccountNonLocked(false);
                    sendEmail.sendEmail(messageSource.getMessage("Account.Locked", null, LocaleContextHolder.getLocale()),messageSource.getMessage("Account.Attempts", null, LocaleContextHolder.getLocale()) ,user.getEmail());
                    userRepo.save(user);
                }else {
                    userAttempt.setAttempts(attempt+1);
                    userAttempt.setLastModified(new Date());
                    userAttemptRepo.save(userAttempt);
                }
            }else {
                UserAttempts userAttempts = new UserAttempts();
                userAttempts.setLastModified(new Date());
                userAttempts.setUsername(username);
                userAttempts.setAttempts(1);

                userAttemptRepo.save(userAttempts);
            }
        }
    }

    public void  userSuccessAttempt(String username){
        UserAttempts userAttempt= userAttemptRepo.findByUsername(username);
        User user = userRepo.findByEmail(username);
        if(null != user){
            if(null != userAttempt){
                userAttemptRepo.deleteByUsername(username);
            }
        }
    }
}
