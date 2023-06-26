package com.blogapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Entity;

@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {

    private String recipient;
    private String salutation;
    private String mainContent;
    private String closing;
    private String signature;
    private String subject;
    private String nameOfSender;

    public static EmailDetails createRegistrationEmail(String recipient) {
        String salutation = "Dear ";
        String mainContent = "Thank you for registering on our website. We are excited to have you as a member of our community!";
        String closing = "If you have any questions or need assistance, feel free to reach out to us.";
        String signature = "Best regards,";
        String nameOfSender="Amar.";
        String subject = "Registration Successful";

        return new EmailDetails(recipient, salutation, mainContent, closing, signature, subject,nameOfSender);
    }
}
