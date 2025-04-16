package com.cyber.cybersecproject.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@EnableAutoConfiguration


@ConfigurationProperties(prefix = "app.email")
@Component
@Entity
@Table(name = "email_recipient")
public class EmailRecipient {

    @Id
    private String email;
    
    public EmailRecipient() {
    }
    
    public EmailRecipient( String email) {
      
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}