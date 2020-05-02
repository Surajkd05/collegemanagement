package com.example.collegeautomationsystem.services;

import com.example.collegeautomationsystem.exception.UserNotFoundException;
import com.example.collegeautomationsystem.model.PasswordToken;
import com.example.collegeautomationsystem.model.User;
import com.example.collegeautomationsystem.model.UserAttempts;
import com.example.collegeautomationsystem.repos.PasswordRepo;
import com.example.collegeautomationsystem.repos.UserAttemptRepo;
import com.example.collegeautomationsystem.repos.UserRepository;
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
    private UserRepository userRepository;

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
        User user = userRepository.findByEmail(email);
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
            throw new UserNotFoundException("No email found");
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
                            user = userRepository.findByEmail(userData.getUserEmail());
                            user.setPassword(passwordEncoder.encode(password));
                            userRepository.save(user);
                            forgotPasswordRepo.deleteByUserEmail(email);

                            UserAttempts userAttempt = userAttemptRepo.findByUsername(email);

                                if (null != userAttempt) {
                                    userAttemptRepo.deleteByUsername(email);
                                    if (!(user.isAccountNonLocked())) {
                                        user.setAccountNonLocked(true);
                                        userRepository.save(user);
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
            throw new UserNotFoundException("User not found");
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
