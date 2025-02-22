package com.food_delivey.food_riding.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {



        private final JavaMailSender mailSender;

        @Value("${spring.mail.username}")
        private String senderEmail;

        public void sendOtpEmail(String recipient, String otp) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                helper.setFrom(senderEmail);
                helper.setTo(recipient);
                helper.setSubject("Food Delivery App - OTP Verification");

                String content = """
                <div style="font-family: Arial, sans-serif; padding: 20px;">
                    <h2 style="color: #2d3436;">Your Verification Code</h2>
                    <p style="font-size: 16px;">Use this code to verify your account:</p>
                    <div style="background: #0984e3; color: white; padding: 10px 20px; 
                        font-size: 24px; border-radius: 5px; display: inline-block;">
                        %s
                    </div>
                    <p style="font-size: 14px; color: #636e72;">This code expires in 5 minutes</p>
                </div>
                """.formatted(otp);

                helper.setText(content, true);
                mailSender.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException("Failed to send email", e);
            }
        }

}
