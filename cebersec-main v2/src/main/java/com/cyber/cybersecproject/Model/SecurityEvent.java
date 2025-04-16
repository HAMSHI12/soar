package com.cyber.cybersecproject.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
public class SecurityEvent {
    @Id
    @JsonProperty("eventId")
    private String eventId = UUID.randomUUID().toString();
    
    private String eventType = "unknown";
    private String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    private String severity = "low";
    
    @JsonProperty("sourceIp")
    private String sourceIp = "0.0.0.0";
    @JsonProperty("destinationIp")
    private String destinationIp = "0.0.0.0";
    private Integer port = 0;
    private String protocol = "none";
    private String action = "none";
    
    @JsonProperty("alertId")
    private String alertId = "none";
    private String description = "none";
    @JsonProperty("username")
    private String username = "none";
    
    @JsonProperty("deviceId")
    private String deviceId = "none";
    @JsonProperty("threatType")
    private String threatType = "none";
    @JsonProperty("filePath")
    private String filePath = "none";
    
    private boolean processed = false; // New field
    
    public SecurityEvent() {
    }
    
    public SecurityEvent(String eventId, String eventType, String timestamp, String severity, String sourceIp, String destinationIp, Integer port, String protocol, String action, String alertId, String description, String username, String deviceId, String threatType, String filePath, boolean processed) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.severity = severity;
        this.sourceIp = sourceIp;
        this.destinationIp = destinationIp;
        this.port = port;
        this.protocol = protocol;
        this.action = action;
        this.alertId = alertId;
        this.description = description;
        this.username = username;
        this.deviceId = deviceId;
        this.threatType = threatType;
        this.filePath = filePath;
        this.processed = processed;
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
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getSeverity() {
        return severity;
    }
    
    public void setSeverity(String severity) {
        this.severity = severity;
    }
    
    public String getSourceIp() {
        return sourceIp;
    }
    
    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }
    
    public String getDestinationIp() {
        return destinationIp;
    }
    
    public void setDestinationIp(String destinationIp) {
        this.destinationIp = destinationIp;
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
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public String getAlertId() {
        return alertId;
    }
    
    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getDeviceId() {
        return deviceId;
    }
    
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    public String getThreatType() {
        return threatType;
    }
    
    public void setThreatType(String threatType) {
        this.threatType = threatType;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public boolean isProcessed() {
        return processed;
    }
    
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}