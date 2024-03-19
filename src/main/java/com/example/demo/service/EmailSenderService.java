package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail, String subject, String body) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("fundraiser20230824@gmail.com");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);
            mailSender.send(message);
            log.info("Mail send to: " + toEmail);;
    }

    public void sendPictureEmail(MultipartFile[] file, String toEmail, String subject, String body) throws MessagingException, IOException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("fundraiser20230824@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        for (int i = 0; i < file.length; i++) {
            mimeMessageHelper.addAttachment(
                    file[i].getOriginalFilename(),
                    new ByteArrayResource(file[i].getBytes()));
        }


        mailSender.send(mimeMessage);
        log.info("Mail send to: " + toEmail);;
    }

    public void sendEmailRanked(String receiverEmailId, String subject, List<String> bannedChampions) throws MessagingException, IOException {

                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setFrom("fundraiser20230824@gmail.com");
                helper.setTo(receiverEmailId);
                helper.setSubject(subject);

                // Read HTML template from file
                String htmlContent = new String(Files.readAllBytes(Paths.get("E:\\intellij\\lol\\src\\main\\resources\\template.html")), StandardCharsets.UTF_8);

                // Set the HTML content of the email
                helper.setText(htmlContent, true);

                // Add inline images
                for (int i = 0; i < bannedChampions.size(); i++) {
                    helper.addInline("ban" + (i + 1), new ClassPathResource("champion/" + bannedChampions.get(i) + ".png"));
                }
                try {
                    mailSender.send(message);
                    System.out.println("Message Sent...Hurrey");
                } catch (Exception exe) {
                    exe.printStackTrace();
                }
            }
        }
