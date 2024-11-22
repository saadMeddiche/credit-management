package com.saadmeddiche.creditmanagement.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleMailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendMail(String to, String subject, String body, List<File> attachments) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(to);

        mimeMessageHelper.setSubject(subject);

        mimeMessageHelper.setText(body);

        mimeMessageHelper.setFrom("CREDIT_MANAGEMENT");

        for (File attachment : attachments) {
            mimeMessageHelper.addAttachment(attachment.getName(), attachment);
        }

        javaMailSender.send(mimeMessage);
    }
}
