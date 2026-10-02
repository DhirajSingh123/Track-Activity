package com.track.activity.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtp(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Track Activity - OTP Verification");
        message.setText(
                "Your Track Activity verification OTP is: " + otp +
                        "\n\nThis OTP is for login verification. Do not share it."
        );

        mailSender.send(message);
    }
}