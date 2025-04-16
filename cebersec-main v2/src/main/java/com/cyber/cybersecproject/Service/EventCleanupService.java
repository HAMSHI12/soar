package com.cyber.cybersecproject.Service;

import com.cyber.cybersecproject.Repository.SecurityEventRepository;
import com.cyber.cybersecproject.Repository.ActionResponseRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventCleanupService {
    
    private final SecurityEventRepository securityEventRepository;
    private final ActionResponseRepository actionResponseRepository;
    
    public EventCleanupService(SecurityEventRepository securityEventRepository,
                               ActionResponseRepository actionResponseRepository) {
        this.securityEventRepository = securityEventRepository;
        this.actionResponseRepository = actionResponseRepository;
    }
    
    @Scheduled(cron = "0 0 0 * * ?") // Daily at midnight
    @Transactional
    public void cleanupOldEvents() {
        // Delete processed events older than 1 day
        securityEventRepository.deleteByProcessedTrue();
        // Delete corresponding action responses
        actionResponseRepository.deleteAll();
        System.out.println("Cleaned up old events and action responses");
    }
}