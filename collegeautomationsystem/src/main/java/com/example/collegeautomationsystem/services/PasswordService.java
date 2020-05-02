package com.example.collegeautomationsystem.services;

import com.example.collegeautomationsystem.exception.UserNotFoundException;
import com.example.collegeautomationsystem.model.PasswordToken;
import com.example.collegeautomationsystem.model.User;
import com.example.collegeautomationsystem.repos.PasswordRepo;
import com.example.collegeautomationsystem.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
public class PasswordService {

    @Autowired
    private PasswordRepo passwordRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    PasswordEncoder passwordEncoder ;

    public String sendToken(String email){

        StringBuilder sb = new StringBuilder();
        User user = userRepository.findByEmail(email);

        try {
            if (null != user) {
                if (user.isActive()) {
                    String token = UUID.randomUUID().toString();

                    PasswordToken passwordToken = new PasswordToken();
                    passwordToken.setGeneratedDate(new Date());
                    passwordToken.setToken(token);
                    passwordToken.setUserEmail(email);

                    passwordRepo.save(passwordToken);

                    sendEmail.sendEmail("Change Password", "To reset your password, please click here:" +
                            "http://localhost:8080/college/app/reset-password?token=" + token + "&email=" + email, email);

                    sb.append("Change your password");
                }else {
                    sb.append("User is not active");
                }
            }
        }catch (NullPointerException ex){
            throw new UserNotFoundException("User not found");
        }
        return sb.toString();
    }

    @Transactional
    @Modifying
    public String resetPassword(String email, String token, String oldPass, String newPass, String confirmPass){

        StringBuilder sb = new StringBuilder();
        User user = userRepository.findByEmail(email);

        PasswordToken userData = passwordRepo.findByUserEmail(email);

        try {
            if(null != userData){
                if(userData.getToken().equals(token)){
                    if(passwordEncoder.matches(oldPass,user.getPassword())){
                        if(newPass.equals(confirmPass)){
                            boolean flag = isTokenExpired(email,userData);

                            if(!flag){
                                user.setPassword(passwordEncoder.encode(newPass));
                                userRepository.save(user);
                                passwordRepo.deleteByUserEmail(email);

                                sendEmail.sendEmail("Password Changed","Your password has changed",email);

                                sb.append("Password successfully changed");
                            }else {
                                sb.append("Token expired");
                            }
                        }else{
                            sb.append("New password and confirm password not matched");
                        }
                    }else {
                        sb.append("Old password is not correct");
                    }
                }else {
                    sb.append("Invalid token");
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
            passwordRepo.deleteByUserEmail(email);
            flag=true;
        }
        return flag;
    }
}
