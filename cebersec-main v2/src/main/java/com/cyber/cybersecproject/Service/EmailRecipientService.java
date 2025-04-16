package com.cyber.cybersecproject.Service;

import com.cyber.cybersecproject.Model.EmailRecipient;
import com.cyber.cybersecproject.Repository.EmailRecipientRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailRecipientService {
    private final EmailRecipientRepository emailRecipientRepository;
    
    @Value("${app.default.email}")
    private String defaultEmail;
    
    public EmailRecipientService(EmailRecipientRepository emailRecipientRepository) {
        this.emailRecipientRepository = emailRecipientRepository;
    }
    
    @PostConstruct
    public void init() {
        if (!emailRecipientRepository.existsById(defaultEmail)) {
            emailRecipientRepository.save(new EmailRecipient(defaultEmail));
            System.out.println("Inserted default email recipient: " + defaultEmail);
        } else {
            System.out.println("Default email recipient already exists: " + defaultEmail);
        }
    }
}