package com.college.automation.system.services;

import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.PasswordToken;
import com.college.automation.system.model.User;
import com.college.automation.system.model.UserAttempts;
import com.college.automation.system.repos.PasswordRepo;
import com.college.automation.system.repos.UserAttemptRepo;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordRepo forgotPasswordRepo;

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    private UserAttemptRepo userAttemptRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MessageSource messageSource;

    public String sendToken(String email){
        StringBuilder sb = new StringBuilder();
        User user = userRepo.findByEmail(email);
        PasswordToken existingUser = forgotPasswordRepo.findByUserEmail(email);
        try {
            if(null!=user){
                if(user.isActive()) {
                    if(null!=existingUser){
                        forgotPasswordRepo.deleteByUserEmail(email);
                    }
                    String token = UUID.randomUUID().toString();

                    PasswordToken forgotPasswordToken = new PasswordToken();
                    forgotPasswordToken.setGeneratedDate(new Date());
                    forgotPasswordToken.setToken(token);
                    forgotPasswordToken.setUserEmail(email);

                    forgotPasswordRepo.save(forgotPasswordToken);

                    sendEmail.sendEmail(messageSource.getMessage("Password.Reset", null, LocaleContextHolder.getLocale()),messageSource.getMessage("Password.Reset.message", null, LocaleContextHolder.getLocale()),email);

                    sb.append(messageSource.getMessage("Password.change", null, LocaleContextHolder.getLocale()));
                }else {
                    sb.append(messageSource.getMessage("Account.NotActive", null, LocaleContextHolder.getLocale()));
                }
            }
        } catch (NullPointerException ex){
            throw new NotFoundException(messageSource.getMessage("NotFound.email", null, LocaleContextHolder.getLocale()));
        }
        return sb.toString();
    }

    @Transactional
    @Modifying
    public String resetPassword(String email,String token,String password,String confirmPassword){

        PasswordToken userData = forgotPasswordRepo.findByUserEmail(email);
        StringBuilder sb = new StringBuilder();
        User user = null;

        try {
            if (null != userData) {
                if (userData.getToken().equals(token)) {
                    if (password.equals(confirmPassword)) {
                        boolean flag = isTokenExpired(email, userData);
                        if (!flag) {
                            user = userRepo.findByEmail(userData.getUserEmail());
                            user.setPassword(passwordEncoder.encode(password));
                            userRepo.save(user);
                            forgotPasswordRepo.deleteByUserEmail(email);

                            UserAttempts userAttempt = userAttemptRepo.findByUsername(email);

                                if (null != userAttempt) {
                                    userAttemptRepo.deleteByUsername(email);
                                    if (!(user.isAccountNonLocked())) {
                                        user.setAccountNonLocked(true);
                                        userRepo.save(user);
                                        sendEmail.sendEmail(messageSource.getMessage("Account.unlocked", null, LocaleContextHolder.getLocale()), messageSource.getMessage("Account.password.unlocked", null, LocaleContextHolder.getLocale()), email);
                                    } else {
                                        sendEmail.sendEmail(messageSource.getMessage("Pass.changed", null, LocaleContextHolder.getLocale()), messageSource.getMessage("Password.changed", null, LocaleContextHolder.getLocale()), email);
                                    }
                                } else {
                                    sendEmail.sendEmail(messageSource.getMessage("Pass.changed", null, LocaleContextHolder.getLocale()), messageSource.getMessage("Password.changed", null, LocaleContextHolder.getLocale()), email);
                                }

                            sb.append(messageSource.getMessage("Password.changed", null, LocaleContextHolder.getLocale()));
                        } else {
                            sb.append(messageSource.getMessage("Password.token.expired", null, LocaleContextHolder.getLocale()));
                        }
                    } else {
                        sb.append(messageSource.getMessage("Password.NotMatched", null, LocaleContextHolder.getLocale()));
                    }
                } else {
                    sb.append(messageSource.getMessage("Token.invalid", null, LocaleContextHolder.getLocale()));
                }
            }
        }catch (NullPointerException ex){
            throw new NotFoundException(messageSource.getMessage("NotFound.user", null, LocaleContextHolder.getLocale()));
        }
        return sb.toString();
    }

    boolean isTokenExpired(String email, PasswordToken userData){

        Date date = new Date();
        long diff = date.getTime() - userData.getGeneratedDate().getTime();
        long diffHours = diff / (60 * 60 * 1000);

        boolean flag = false;
        if (diffHours > 24) {
            forgotPasswordRepo.deleteByUserEmail(email);
            flag=true;
        }
        return flag;
    }
}
