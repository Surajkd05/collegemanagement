package com.college.automation.system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SendEmail {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String subject,String text,String sentTo){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(text);
        message.setSubject(subject);
        message.setFrom("sd9808006@gmail.com");
        message.setTo(sentTo);

        javaMailSender.send(message);
    }
}
