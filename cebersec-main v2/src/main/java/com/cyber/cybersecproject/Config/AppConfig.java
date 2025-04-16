package com.cyber.cybersecproject.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private Notifications notifications;
    private Actions actions;
    
    public Notifications getNotifications() {
        return notifications;
    }
    
    public void setNotifications(Notifications notifications) {
        this.notifications = notifications;
    }
    
    public Actions getActions() {
        return actions;
    }
    
    public void setActions(Actions actions) {
        this.actions = actions;
    }
    
    public static class Notifications {
        private List<String> severities;
        private List<String> threatTypes;
        
        public List<String> getSeverities() {
            return severities;
        }
        
        public void setSeverities(List<String> severities) {
            this.severities = severities;
        }
        
        public List<String> getThreatTypes() {
            return threatTypes;
        }
        
        public void setThreatTypes(List<String> threatTypes) {
            this.threatTypes = threatTypes;
        }
    }
    
    public static class Actions {
        private List<ActionRule> rules;
        
        public List<ActionRule> getRules() {
            return rules;
        }
        
        public void setRules(List<ActionRule> rules) {
            this.rules = rules;
        }
    }
    public static class ActionRule {
        private String eventType;
        private String sourceIp;
        private String destinationIp;
        private String action;
        private String threatType;
        private String deviceId;
        
        public ActionRule(String eventType, String sourceIp, String destinationIp, String action, String threatType, String deviceId) {
            this.eventType = eventType;
            this.sourceIp = sourceIp;
            this.destinationIp = destinationIp;
            this.action = action;
            this.threatType = threatType;
            this.deviceId = deviceId;
        }
        
        public ActionRule() {
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
        
        public String getAction() {
            return action;
        }
        
        public void setAction(String action) {
            this.action = action;
        }
    }
    
}