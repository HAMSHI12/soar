package com.cyber.cybersecproject.Config;


public class ActionRule {
    private String eventType;
    private String sourceIp;
    private String destinationIp;
    private String threatType;
    private String deviceId;
    private String action;
    
    public ActionRule() {
    }
    
    public ActionRule(String eventType, String sourceIp, String destinationIp, String threatType, String deviceId, String action) {
        this.eventType = eventType;
        this.sourceIp = sourceIp;
        this.destinationIp = destinationIp;
        this.threatType = threatType;
        this.deviceId = deviceId;
        this.action = action;
    }
    
    public String getEventType() {
        return eventType;
    }
    
    public void setEventType(String eventType) {
        this.eventType = eventType;
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
    
    public String getThreatType() {
        return threatType;
    }
    
    public void setThreatType(String threatType) {
        this.threatType = threatType;
    }
    
    public String getDeviceId() {
        return deviceId;
    }
    
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
}