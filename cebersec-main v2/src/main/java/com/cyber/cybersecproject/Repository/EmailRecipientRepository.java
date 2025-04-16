package com.cyber.cybersecproject.Repository;

import com.cyber.cybersecproject.Model.EmailRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRecipientRepository extends JpaRepository<EmailRecipient, String> {

}