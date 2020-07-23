package com.college.automation.system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGridAPI;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.IOException;

@Service
public class SendEmail {

    @Autowired
    private SendGridAPI sendGridAPI;

    @Async
    public void sendEmail(String subject,String text,String sentTo){
        Email from = new Email("sd9808006@gmail.com");
        Email to = new Email(sentTo);
        Content content = new Content("text/plain", text);
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST) ;
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGridAPI.api(request);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}