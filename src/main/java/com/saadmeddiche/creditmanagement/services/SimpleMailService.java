package com.saadmeddiche.creditmanagement.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.util.Pair;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleMailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendMail(String from, String to, String subject, String body, List<Pair<String , byte[]>> attachments) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(from);

        mimeMessageHelper.setTo(to);

        mimeMessageHelper.setSubject(subject);

        mimeMessageHelper.setText(body);

        for (Pair<String , byte[]> attachment : attachments) {
            mimeMessageHelper.addAttachment(attachment.getFirst(), new ByteArrayResource(attachment.getSecond()));
        }

        javaMailSender.send(mimeMessage);
    }
}
