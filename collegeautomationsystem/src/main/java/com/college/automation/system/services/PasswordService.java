package com.college.automation.system.services;

import com.college.automation.system.exceptions.BadRequestException;
import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.PasswordRepo;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

    @Autowired
    private MessageSource messageSource;

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
                    sendEmail.sendEmail(messageSource.getMessage("Pass.changed", null, LocaleContextHolder.getLocale()), messageSource.getMessage("Password.changed", null, LocaleContextHolder.getLocale()), email);

                    sb.append(messageSource.getMessage("Password.changed", null, LocaleContextHolder.getLocale()));
                } else {
                    throw new BadRequestException(messageSource.getMessage("Passwords.NotMatched", null, LocaleContextHolder.getLocale()));
                }
            } else {
                throw new BadRequestException(messageSource.getMessage("Password.old", null, LocaleContextHolder.getLocale()));
            }
        } else {
            throw new NotFoundException(messageSource.getMessage("NotFound.user", null, LocaleContextHolder.getLocale()));
        }

        return sb.toString();
    }
}
