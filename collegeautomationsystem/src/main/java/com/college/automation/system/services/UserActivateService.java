
package com.college.automation.system.services;

import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.User;
import com.college.automation.system.model.VerificationToken;
import com.college.automation.system.repos.UserActivateRepo;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
public class UserActivateService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    private UserActivateRepo userActivateRepo;

    @Autowired
    private MessageSource messageSource;

    @Transactional
    public String activateUser(String token) {

        VerificationToken userActivate = userActivateRepo.findByToken(token);
        StringBuilder sb=new StringBuilder();
        User user=null;

        if(null!=userActivate)
        {
            try {
                String email=userActivate.getUserEmail();
                if (!email.equals(null)) {
                    boolean flag=isTokenExpired(email, userActivate);
                    if(!flag) {
                        user = userRepo.findByEmail(userActivate.getUserEmail());
                        boolean isActivated= activateUser(email,user);
                        if(isActivated)
                        {
                            sb.append(messageSource.getMessage("Activated.successful", null, LocaleContextHolder.getLocale()));
                        }
                    }else {
                        sb.append(messageSource.getMessage("Token.expired", null, LocaleContextHolder.getLocale()));
                    }
                }
            } catch (NullPointerException ex) {
                throw new NotFoundException(messageSource.getMessage("NotFound.email", null, LocaleContextHolder.getLocale()));
            }

        }else{
            sb.append(messageSource.getMessage("Token.invalid", null, LocaleContextHolder.getLocale()));
        }
        return sb.toString();
    }

    boolean activateUser(String email, User user){
        boolean flag=false;
        try {
            user.setActive(true);
            userRepo.save(user);
            sendEmail.sendEmail(messageSource.getMessage("Account.Activated", null, LocaleContextHolder.getLocale()), messageSource.getMessage("Account.activatedMessage", null, LocaleContextHolder.getLocale()), email);
            userActivateRepo.deleteByUserEmail(email);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    boolean isTokenExpired(String email, VerificationToken customerActivate ){

        Date date = new Date();
        long diff = date.getTime() - customerActivate.getGeneratedDate().getTime();
        long diffHours = diff / (60 * 60 * 1000);
        boolean flag=false;
        // token expire case
        if (diffHours > 24) {
            userActivateRepo.deleteByUserEmail(email);

            String newToken = UUID.randomUUID().toString();

            VerificationToken localUserActivate = new VerificationToken();
            localUserActivate.setToken(newToken);
            localUserActivate.setUserEmail(email);
            localUserActivate.setGeneratedDate(new Date());

            userActivateRepo.save(localUserActivate);

            sendEmail.sendEmail(messageSource.getMessage("Account.reActivate", null, LocaleContextHolder.getLocale()),messageSource.getMessage("Account.confirm", null, LocaleContextHolder.getLocale())+newToken,email);
            flag=true;
        }
        return flag;
    }

    @Transactional
    public String resendLink(String email) {

        User user = userRepo.findByEmail(email);
        StringBuilder sb = new StringBuilder();
        try {
            if (!user.getEmail().equals(null)) {
                if (user.isActive()) {
                   return sb.append(messageSource.getMessage("Account.already.activated", null, LocaleContextHolder.getLocale())).toString();
                }else {
                    userActivateRepo.deleteByUserEmail(email);

                    String newToken = UUID.randomUUID().toString();

                    VerificationToken localUserActivate = new VerificationToken();
                    localUserActivate.setToken(newToken);
                    localUserActivate.setUserEmail(email);
                    localUserActivate.setGeneratedDate(new Date());

                    userActivateRepo.save(localUserActivate);

                    sendEmail.sendEmail(messageSource.getMessage("Account.reActivated", null, LocaleContextHolder.getLocale()),messageSource.getMessage("Account.confirm", null,LocaleContextHolder.getLocale())+newToken,email);

                    sb.append(messageSource.getMessage("Message.successful", null, LocaleContextHolder.getLocale()));
                }
            }
        } catch (NullPointerException ex) {
            throw new NotFoundException(messageSource.getMessage("NotFound.email", null, LocaleContextHolder.getLocale()));
        }
        return sb.toString();
    }
}

