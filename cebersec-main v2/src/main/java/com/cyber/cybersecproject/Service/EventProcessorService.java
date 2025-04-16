package com.cyber.cybersecproject.Service;

import com.cyber.cybersecproject.Model.ActionResponse;
import com.cyber.cybersecproject.Model.SecurityEvent;
import com.cyber.cybersecproject.Model.EmailRecipient;
import com.cyber.cybersecproject.Repository.SecurityEventRepository;
import com.cyber.cybersecproject.Repository.EmailRecipientRepository;
import com.cyber.cybersecproject.Repository.ActionResponseRepository;
import com.cyber.cybersecproject.Config.AppConfig;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventProcessorService {
    
    private final SecurityEventRepository securityEventRepository;
    private final EmailService emailService;
    private final EmailRecipientRepository emailRecipientRepository;
    private final ActionResponseRepository actionResponseRepository;
    private final List<AppConfig.ActionRule> actionRules;
    private final AppConfig appConfig; // 👈 ADD THIS FIELD
    
    private static final int BATCH_SIZE = 50;
    
    public EventProcessorService(SecurityEventRepository securityEventRepository,
                                 EmailService emailService,
                                 EmailRecipientRepository emailRecipientRepository,
                                 ActionResponseRepository actionResponseRepository,
                                 AppConfig appConfig) {
        this.securityEventRepository = securityEventRepository;
        this.emailService = emailService;
        this.emailRecipientRepository = emailRecipientRepository;
        this.actionResponseRepository = actionResponseRepository;
        this.appConfig = appConfig; // 👈 ASSIGN IT HERE
        this.actionRules = appConfig.getActions ().getRules ();
    }
    @Transactional
    public List<ActionResponse> processEvents() {
        List<SecurityEvent> events = securityEventRepository.findByProcessedFalse();
        
        if (events.isEmpty()) {
            System.out.println("No unprocessed events found in database");
            return actionResponseRepository.findAll();
        }
        
        // Limit to BATCH_SIZE
        List<SecurityEvent> eventsToProcess = events.size() > BATCH_SIZE
                ? events.subList(0, BATCH_SIZE)
                : events;
        
        List<ActionResponse> responses = new ArrayList<>();
        List<SecurityEvent> eventsToUpdate = new ArrayList<>();
        
        for (SecurityEvent event : eventsToProcess) {
            ActionResponse response = processEvent(event);
            if (response != null) {
                responses.add(response);
                event.setProcessed(true);
                eventsToUpdate.add(event);
            }
        }
        
        if (!responses.isEmpty()) {
            actionResponseRepository.saveAll(responses);
        }
        if (!eventsToUpdate.isEmpty()) {
            securityEventRepository.saveAll(eventsToUpdate);
        }
        
        return responses;
    }
    
    private ActionResponse processEvent(SecurityEvent event) {
        if (event == null || event.getEventType() == null) {
            return new ActionResponse(
                    event != null ? event.getEventId() : "unknown",
                    "unknown",
                    "Ignored",
                    "Invalid or null event",
                    "0.0.0.0",
                    0,
                    "none",
                    "unknown",
                    "unknown"
            );
        }
        
        ActionResponse response = new ActionResponse();
        response.setEventId(event.getEventId());
        response.setEventType(event.getEventType());
        response.setSourceIp(event.getSourceIp() != null ? event.getSourceIp() : "0.0.0.0");
        response.setPort(event.getPort() != null ? event.getPort() : 0);
        response.setProtocol(event.getProtocol() != null ? event.getProtocol() : "none");
        response.setCybersecurityDevice(getCybersecurityDevice(event.getEventType()));
        response.setSeverity(event.getSeverity() != null ? event.getSeverity() : "unknown");
        String emailBody = null;
        String actionTaken = null;
        
        for (AppConfig.ActionRule rule : actionRules) {
            if (rule.getEventType() != null && !rule.getEventType().equalsIgnoreCase(event.getEventType())) {
                continue;
            }
            if (rule.getSourceIp() != null && !rule.getSourceIp().equals(event.getSourceIp())) {
                continue;
            }
            if (rule.getDestinationIp() != null && !rule.getDestinationIp().equals(event.getDestinationIp())) {
                continue;
            }
            if (rule.getThreatType() != null && !rule.getThreatType().equalsIgnoreCase(event.getThreatType())) {
                continue;
            }
            if (rule.getDeviceId() != null && !rule.getDeviceId().equals(event.getDeviceId())) {
                continue;
            }
            actionTaken = rule.getAction();
            break;
        }
        
        switch (event.getEventType().toLowerCase()) {
            case "firewall":
                if (actionTaken != null) {
                    response.setActionTaken(actionTaken);
                    response.setDetails("Firewall action: " + actionTaken + " for source IP: " +
                            event.getSourceIp() + " on port " + event.getPort() +
                            " (" + event.getProtocol() + ")");
                    emailBody = String.format("Firewall Alert: %s for IP %s on port %d (%s, severity: %s).",
                            actionTaken, event.getSourceIp(), event.getPort(),
                            event.getProtocol(), event.getSeverity());
                } else if ("high".equalsIgnoreCase(event.getSeverity()) ||
                        "critical".equalsIgnoreCase(event.getSeverity())) {
                    if (event.getSourceIp() == null || event.getPort() == null) {
                        response.setActionTaken("Ignored");
                        response.setDetails("Invalid firewall event: missing sourceIp or port");
                        System.out.println("Skipping email for firewall event " + event.getEventId());
                        break;
                    }
                    response.setActionTaken(event.getAction());
                    response.setDetails("Firewall action: " + event.getAction() + " for source IP: " +
                            event.getSourceIp() + " on port " + event.getPort() +
                            " (" + event.getProtocol() + ")");
                    emailBody = String.format("Firewall Alert: %s for IP %s on port %d (%s, severity: %s).",
                            event.getAction(), event.getSourceIp(), event.getPort(),
                            event.getProtocol(), event.getSeverity());
                } else {
                    response.setActionTaken("Logged");
                    response.setDetails("Logged firewall event from IP: " +
                            (event.getSourceIp() != null ? event.getSourceIp() : "unknown") +
                            " on port " + (event.getPort() != null ? event.getPort() : 0));
                }
                break;
            
            case "siem":
                if (actionTaken != null) {
                    response.setActionTaken(actionTaken);
                    response.setDetails("SIEM action: " + actionTaken + ", User: " + event.getUsername());
                    emailBody = String.format("SIEM Alert: %s - %s (User: %s, severity: %s)",
                            event.getAlertId(), event.getDescription(),
                            event.getUsername(), event.getSeverity());
                } else if (event.getAlertId() == null || event.getDescription() == null || event.getUsername() == null) {
                    response.setActionTaken("Ignored");
                    response.setDetails("Invalid SIEM event: missing alertId, description, or username");
                    System.out.println("Skipping email for SIEM event " + event.getEventId());
                    break;
                } else {
                    response.setActionTaken("Alert Logged");
                    response.setDetails("SIEM alert ID: " + event.getAlertId() + ", User: " + event.getUsername());
                    emailBody = String.format("SIEM Alert: %s - %s (User: %s, severity: %s)",
                            event.getAlertId(), event.getDescription(),
                            event.getUsername(), event.getSeverity());
                }
                break;
            
            case "endpoint":
                if (actionTaken != null) {
                    response.setActionTaken(actionTaken);
                    response.setDetails("Endpoint action: " + actionTaken + " for device ID: " + event.getDeviceId());
                    emailBody = String.format("Endpoint Alert: %s on device %s at %s (severity: %s).",
                            event.getThreatType(), event.getDeviceId(),
                            event.getFilePath() != null ? event.getFilePath() : "unknown",
                            event.getSeverity());
                } else if ("critical".equalsIgnoreCase(event.getSeverity())) {
                    if (event.getDeviceId() == null || event.getThreatType() == null) {
                        response.setActionTaken("Ignored");
                        response.setDetails("Invalid endpoint event: missing deviceId or threatType");
                        System.out.println("Skipping email for endpoint event " + event.getEventId());
                        break;
                    }
                    response.setActionTaken("Quarantined Device");
                    response.setDetails("Quarantined device ID: " + event.getDeviceId() +
                            " due to " + event.getThreatType());
                    emailBody = String.format("Endpoint Alert: %s on device %s at %s (severity: %s).",
                            event.getThreatType(), event.getDeviceId(),
                            event.getFilePath() != null ? event.getFilePath() : "unknown",
                            event.getSeverity());
                } else {
                    response.setActionTaken("Logged");
                    response.setDetails("Logged endpoint event from device: " +
                            (event.getDeviceId() != null ? event.getDeviceId() : "unknown") +
                            " with threat: " +
                            (event.getThreatType() != null ? event.getThreatType() : "none"));
                }
                break;
            
            default:
                response.setActionTaken("Ignored");
                response.setDetails("Unknown event type: " + event.getEventType());
                break;
        }
        
        if (emailBody != null && shouldSendNotification(event)) {
            sendEmailsAsync(event.getEventType(), event.getSeverity(), emailBody);
        }
        
        return response;
    }
    
    private boolean shouldSendNotification(SecurityEvent event) {
        return appConfig.getNotifications().getSeverities().stream()
                .anyMatch(s -> s.equalsIgnoreCase(event.getSeverity())) ||
                appConfig.getNotifications().getThreatTypes().stream()
                        .anyMatch(t -> t.equalsIgnoreCase(event.getThreatType()));
    }
    
    @Async
    public void sendEmailsAsync(String eventType, String severity, String emailBody) {
        List<EmailRecipient> recipients = emailRecipientRepository.findAll();
        if (recipients.isEmpty()) {
            System.out.println("No email recipients found for event: " + eventType +
                    ", severity: " + severity);
            return;
        }
        System.out.println("Found " + recipients.size() + " email recipients for event: " +
                eventType + ", severity: " + severity);
        for (EmailRecipient recipient : recipients) {
            try {
                emailService.sendEmail(recipient.getEmail(),
                        "Security Alert: " + eventType,
                        emailBody);
                System.out.println("Sent email to: " + recipient.getEmail() + " for event: " +
                        eventType + " with body: " + emailBody);
            } catch (Exception e) {
                System.err.println("Failed to send email to " + recipient.getEmail() +
                        " for event " + eventType + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private String getCybersecurityDevice(String eventType) {
        switch (eventType.toLowerCase()) {
            case "firewall":
                return "Firewall-X";
            case "siem":
                return "SIEM-System";
            case "endpoint":
                return "Endpoint-Protector";
            default:
                return "unknown";
        }
    }
}