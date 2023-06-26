package com.blogapp.services.impl;

import com.blogapp.entities.EmailDetails;
import com.blogapp.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendSimpleMail(String to, EmailDetails emailDetails) {
        try {
            emailDetails.setRecipient(to);

            String recipient = emailDetails.getRecipient();
            String salutation = emailDetails.getSalutation();
            String mainContent = emailDetails.getMainContent();
            String closing = emailDetails.getClosing();
            String signature = emailDetails.getSignature();
            String nameOfSender = emailDetails.getNameOfSender();


            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject(emailDetails.getSubject());
            String emailContent = salutation + to +","+ "<br><br>" +
                    mainContent + "<br><br>" +
                    closing + "<br><br>" +
                    signature +"<br><br>" +
                    nameOfSender;
            mimeMessageHelper.setText(emailContent, true);

            javaMailSender.send(mimeMessage);

            System.out.println("Successful");
        } catch (MessagingException e) {
           // return "Error while Sending Mail";
            System.out.println("Error while Sending Mail");
        }



    }

//    @Override
//    public String sendMailWithAttachment(EmailDetails details) {
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
//            mimeMessageHelper.setFrom(sender);
//            mimeMessageHelper.setTo(details.getRecipient());
//            mimeMessageHelper.setSubject(details.getSubject());
//
//            String emailBody = details.getSalutation() + "<br><br>" +
//                    details.getMainContent() + "<br><br>" +
//                    details.getClosing() + "<br><br>" +
//                    details.getSignature();
//            mimeMessageHelper.setText(emailBody, true);
//
//            if (details.getAttachment() != null && !details.getAttachment().isEmpty()) {
//                FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
//                mimeMessageHelper.addAttachment(file.getFilename(), file);
//            }
//
//            javaMailSender.send(mimeMessage);
//            return "Mail sent successfully";
//        } catch (MessagingException e) {
//            return "Error while sending mail";
//        }
 //   }
}
