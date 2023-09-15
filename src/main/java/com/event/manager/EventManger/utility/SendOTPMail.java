package com.event.manager.EventManger.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class SendOTPMail {
    private final JavaMailSender javaMailSender;
    @Autowired
    public SendOTPMail(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendOTPByEmail(String userEmail,String otp) {
        try {

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userEmail);
            mailMessage.setSubject("Verification Code for Event Manger");
            mailMessage.setText("Your OTP code is: " + otp +" and it is valid for 10 minutes only");

            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }

}

