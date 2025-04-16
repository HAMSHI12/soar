package com.cyber.cybersecproject.Repository;

import com.cyber.cybersecproject.Model.SecurityEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityEventRepository extends JpaRepository<SecurityEvent, Long> {

    List<SecurityEvent> findByProcessedFalse();
    void deleteByProcessedTrue();
    
}