package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
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

    public void sendEmail2(String receiverEmailId, String subject, List<String> bannedChampions)
    {

        MimeMessagePreparator preparator = new MimeMessagePreparator()
        {

            public void prepare(MimeMessage mimeMessage) throws Exception
            {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                helper.setFrom("fundraiser20230824@gmail.com");
                helper.setTo(receiverEmailId);
                helper.setSubject(subject);

                /*
                 * Add an inline resource. use the true flag to indicate you need a multipart
                 * message
                 */
                helper.setText(
                        "<html>" +
                                "<head>" +
                                "<style>" +
                                ".container {" +
                                "display: flex;" +
                                "justify-content: space-between;" +
                                "width: 100%;" + // Make the container 100% width
                                "max-width: 1200px;" + // Limit the maximum width to 1200px
                                "margin: 0 auto;" + // Center the container horizontally
                                "}" +
                                ".team {" +
                                "text-align: center;" + // Center-align text horizontally
                                "}" +
                                ".second-team {" +
                                "margin-left: auto;" + // Align to the far right
                                "text-align: center;" + // Right-align text
                                "}" +
                                "</style>" +
                                "</head>" +
                                "<body>" +
                                "<div class='container'>" +
                                "<div class='team'>" +
                                "<p style='font-size: 24px;'>First team bans</p>" +
                                "<img src='cid:ban1' width='40px'>" + " " +
                                "<img src='cid:ban2' width='40px'>" + " " +
                                "<img src='cid:ban3' width='40px'>" + " " +
                                "<img src='cid:ban4' width='40px'>" + " " +
                                "<img src='cid:ban5' width='40px'>" + " " +
                                "</div>" +
                                "<div class='team second-team'>" +
                                "<p style='font-size: 24px;'>Second team bans</p>" +
                                "<img src='cid:ban6' width='40px'>" + " " +
                                "<img src='cid:ban7' width='40px'>" + " " +
                                "<img src='cid:ban8' width='40px'>" + " " +
                                "<img src='cid:ban9' width='40px'>" + " " +
                                "<img src='cid:ban10' width='40px'>" + " " +
                                "</div>" +
                                "</div>" +
                                "</body>" +
                                "</html>",
                        true);
                helper.addInline("ban1", new ClassPathResource("champion/" + bannedChampions.get(0) + ".png"));
                helper.addInline("ban2", new ClassPathResource("champion/" + bannedChampions.get(1) + ".png"));
                helper.addInline("ban3", new ClassPathResource("champion/" + bannedChampions.get(2) + ".png"));
                helper.addInline("ban4", new ClassPathResource("champion/" + bannedChampions.get(3) + ".png"));
                helper.addInline("ban5", new ClassPathResource("champion/" + bannedChampions.get(4) + ".png"));
                helper.addInline("ban6", new ClassPathResource("champion/" + bannedChampions.get(5) + ".png"));
                helper.addInline("ban7", new ClassPathResource("champion/" + bannedChampions.get(6) + ".png"));
                helper.addInline("ban8", new ClassPathResource("champion/" + bannedChampions.get(7) + ".png"));
                helper.addInline("ban9", new ClassPathResource("champion/" + bannedChampions.get(8) + ".png"));
                helper.addInline("ban10", new ClassPathResource("champion/" + bannedChampions.get(9) + ".png"));
            }
        };

        try
        {
            mailSender.send(preparator);
            System.out.println("Message Sent...Hurrey");
        }
        catch (Exception exe)
        {
            exe.printStackTrace();
        }
    }
}
