
package com.college.automation.system.services;

import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.User;
import com.college.automation.system.model.VerificationToken;
import com.college.automation.system.repos.UserActivateRepo;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
                            sb.append("Successfully activated");
                        }
                    }else {
                        sb.append("Token Expired");
                    }
                }
            } catch (NullPointerException ex) {
                throw new NotFoundException( "No email found");
            }

        }else{
            sb.append("Invalid Token");
        }
        return sb.toString();
    }

    boolean activateUser(String email, User user){
        boolean flag=false;
        try {
            user.setActive(true);
            userRepo.save(user);
            sendEmail.sendEmail("ACCOUNT ACTIVATED", "Your account has been activated", email);
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

            sendEmail.sendEmail("RE-ACCOUNT ACTIVATE TOKEN","To confirm your account, please click here : "
                    +"http://localhost:8080/college/register/confirm-account?token="+newToken,email);
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
                   return sb.append("Account already active").toString();
                }else {
                    userActivateRepo.deleteByUserEmail(email);

                    String newToken = UUID.randomUUID().toString();

                    VerificationToken localUserActivate = new VerificationToken();
                    localUserActivate.setToken(newToken);
                    localUserActivate.setUserEmail(email);
                    localUserActivate.setGeneratedDate(new Date());

                    userActivateRepo.save(localUserActivate);

                    sendEmail.sendEmail("RE-ACCOUNT ACTIVATE TOKEN","To confirm your account, please click here : "
                            +"https://college-management-system2.herokuapp.com/college/auth/confirm-account?token="+newToken,email);

                    sb.append("Successful");
                }
            }
        } catch (NullPointerException ex) {
            throw new NotFoundException("No email found");
        }
        return sb.toString();
    }
}

