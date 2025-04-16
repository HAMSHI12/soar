package com.cyber.cybersecproject.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "action_response")
public class ActionResponse {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String eventId;
    private String eventType;
    private String actionTaken;
    private String details;
    private String sourceIp;
    private Integer port;
    private String protocol;
    private String cybersecurityDevice;
    private String severity;
    
    public ActionResponse() {
    }
    
    public ActionResponse(String eventId, String eventType, String actionTaken, String details,
                          String sourceIp, Integer port, String protocol, String cybersecurityDevice,
                          String severity) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.actionTaken = actionTaken;
        this.details = details;
        this.sourceIp = sourceIp;
        this.port = port;
        this.protocol = protocol;
        this.cybersecurityDevice = cybersecurityDevice;
        this.severity = severity;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getEventId() {
        return eventId;
    }
    
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
    public String getEventType() {
        return eventType;
    }
    
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    public String getActionTaken() {
        return actionTaken;
    }
    
    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    public String getSourceIp() {
        return sourceIp;
    }
    
    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }
    
    public Integer getPort() {
        return port;
    }
    
    public void setPort(Integer port) {
        this.port = port;
    }
    
    public String getProtocol() {
        return protocol;
    }
    
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    
    public String getCybersecurityDevice() {
        return cybersecurityDevice;
    }
    
    public void setCybersecurityDevice(String cybersecurityDevice) {
        this.cybersecurityDevice = cybersecurityDevice;
    }
    
    public String getSeverity() {
        return severity;
    }
    
    public void setSeverity(String severity) {
        this.severity = severity;
    }
}