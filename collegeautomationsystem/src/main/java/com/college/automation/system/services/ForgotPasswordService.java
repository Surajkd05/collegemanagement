package com.college.automation.system.services;

import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.PasswordToken;
import com.college.automation.system.model.User;
import com.college.automation.system.model.UserAttempts;
import com.college.automation.system.repos.PasswordRepo;
import com.college.automation.system.repos.UserAttemptRepo;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

                    sendEmail.sendEmail("Reset your password","To reset your password, please click here : "
                            +"http://localhost:8080/college/forgotPassword/reset-password?token="+token+"&email="+email,email);

                    sb.append("Change your password");
                }else {
                    sb.append("Account is not active");
                }
            }
        } catch (NullPointerException ex){
            throw new NotFoundException("No email found");
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
                                        sendEmail.sendEmail("Account Unlocked and password changed", "Your password is successfully changed and account is unlocked.", email);
                                    } else {
                                        sendEmail.sendEmail("Password Changed", "Your password has changed", email);
                                    }
                                } else {
                                    sendEmail.sendEmail("Password Changed", "Your password has changed", email);
                                }

                            sb.append("Password successfully changed");
                        } else {
                            sb.append("Password not updated as token expired");
                        }
                    } else {
                        sb.append("Password not matched");
                    }
                } else {
                    sb.append("Invalid Token");
                }
            }
        }catch (NullPointerException ex){
            throw new NotFoundException("User not found");
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
