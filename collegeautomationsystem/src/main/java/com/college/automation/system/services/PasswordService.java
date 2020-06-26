package com.college.automation.system.services;

import com.college.automation.system.exceptions.BadRequestException;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.PasswordRepo;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PasswordService {

    @Autowired
    private PasswordRepo passwordRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    @Modifying
    public String updatePassword(String username, String oldPass, String newPass, String confirmPass) {

        StringBuilder sb = new StringBuilder();
        Optional<User> user = Optional.ofNullable(userRepo.findByEmail(username));

        if (user.isPresent()) {
            if (passwordEncoder.matches(oldPass, user.get().getPassword())) {

                if (newPass.equals(confirmPass)) {
                    user.get().setPassword(passwordEncoder.encode(newPass));
                    userRepo.save(user.get());

                    String email = user.get().getEmail();
                    sendEmail.sendEmail("Password Changed", "Your password has changed", email);

                    sb.append("Password successfully changed");
                } else {
                    throw new BadRequestException("New password and confirm password not matched");
                }
            } else {
                throw new BadRequestException("Old password is not correct");
            }
        } else {
            throw new NotFoundException("User not found");
        }

        return sb.toString();
    }
}
