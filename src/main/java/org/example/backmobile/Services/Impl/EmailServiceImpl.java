package org.example.backmobile.Services.Impl;

import org.example.backmobile.Services.Interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;



@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    @Override
    public void sendEmail(String to, String subject, String message, String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(to);
            helper.setSubject(subject);

            // If HTML content is provided, use it; otherwise, use the plain text message
            if (htmlContent != null && !htmlContent.isEmpty()) {
                helper.setText(htmlContent, true); // Set HTML content
            } else {
                helper.setText(message); // Set plain text content
            }

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Handle exception (logging, rethrowing, etc.)
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void sendEmail(String to, String subject, String message) {
        // Call the main sendEmail method with null HTML content
        sendEmail(to, subject, message, null);
    }

    @Async
    @Override
    public void sendEmail(String to, String message) {
        // Provide a default subject and call the main sendEmail method
        sendEmail(to, "No Subject", message, null);
    }
}
