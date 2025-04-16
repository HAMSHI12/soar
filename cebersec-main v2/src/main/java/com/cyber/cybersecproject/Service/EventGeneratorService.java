package com.cyber.cybersecproject.Service;

import com.cyber.cybersecproject.Model.SecurityEvent;
import com.cyber.cybersecproject.Repository.SecurityEventRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class EventGeneratorService {
    
    private final SecurityEventRepository securityEventRepository;
    private final Random random = new Random();
    
    @Value("${app.events.count.firewall}")
    private int firewallEventCount;
    
    @Value("${app.events.count.siem}")
    private int siemEventCount;
    
    @Value("${app.events.count.endpoint}")
    private int endpointEventCount;
    
    @Value("${app.events.schedule.interval}")
    private long scheduleInterval;
    
    private static final List<String> SEVERITIES = Arrays.asList(
            "low", "medium", "high", "critical", "normal", "system", "update"
    );
    
    private static final List<String> FIREWALL_ACTIONS = Arrays.asList(
            "attempted_connection", "blocked", "allowed", "drop", "deny", "accept",
            "reject", "forward", "redirect", "log"
    );
    
    private static final List<String> THREAT_TYPES = Arrays.asList(
            "malware_detected", "ransomware", "phishing_attempt", "virus",
            "privilege_escalation", "trojan", "worm", "spyware", "adware",
            "rootkit", "botnet", "ddos", "sql_injection", "xss", "brute_force"
    );
    
    private static final List<String> USERS = Arrays.asList(
            "john.doe", "jane.smith", "admin", "guest",
            "mohammed.ali", "fatima.hassan", "ahmed.khalid", "noor.salem",
            "omar.youssef", "layla.mahmoud", "hassan.ibrahim", "sara.abdullah",
            "khaled.nasser", "amina.zahra"
    );
    
    private static final List<String> IPS = Arrays.asList(
            "192.168.0.1", "192.168.1.100", "10.0.0.50", "172.16.254.1", // Private
            "203.0.113.10", "198.51.100.25", "93.184.216.34", // Public
            "8.8.8.8", "1.1.1.1", // Well-known
            "169.254.0.1", // Link-local
            "239.255.255.250" // Multicast
    );
    
    private static final List<String> PROTOCOLS = Arrays.asList(
            "TCP", "UDP", "ICMP", "HTTP", "HTTPS", "FTP", "SSH",
            "SMTP", "DNS", "SNMP", "TELNET", "RDP"
    );
    
    private static final List<Integer> PORTS = Arrays.asList(
            80, 443, 22, 21, 25, 53, 161, 23, 3389,
            8080, 3306, 5432, 445, 137, 138, 139
    );
    
    public EventGeneratorService(SecurityEventRepository securityEventRepository) {
        this.securityEventRepository = securityEventRepository;
    }
    
    public void generateRandomEvents() {
        List<SecurityEvent> events = new ArrayList<>();
        events.addAll(generateFirewallEvents(firewallEventCount));
        events.addAll(generateSiemEvents(siemEventCount));
        events.addAll(generateEndpointEvents(endpointEventCount));
        
        try {
            securityEventRepository.saveAll(events);
            System.out.println("Generated and saved " + events.size() + " events to database at " + getCurrentTimestamp());
        } catch (Exception e) {
            System.err.println("Error saving events to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private List<SecurityEvent> generateFirewallEvents(int count) {
        List<SecurityEvent> events = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            SecurityEvent event = new SecurityEvent();
            event.setEventId(UUID.randomUUID().toString());
            event.setEventType("firewall");
            event.setTimestamp(getCurrentTimestamp());
            event.setSourceIp(IPS.get(random.nextInt(IPS.size())));
            event.setDestinationIp(IPS.get(random.nextInt(IPS.size())));
            event.setPort(PORTS.get(random.nextInt(PORTS.size())));
            event.setProtocol(PROTOCOLS.get(random.nextInt(PROTOCOLS.size())));
            event.setAction(FIREWALL_ACTIONS.get(random.nextInt(FIREWALL_ACTIONS.size())));
            event.setSeverity(SEVERITIES.get(random.nextInt(SEVERITIES.size())));
            event.setAlertId("none");
            event.setDescription("none");
            event.setUsername("none");
            event.setDeviceId("none");
            event.setThreatType("none");
            event.setFilePath("none");
            events.add(event);
        }
        return events;
    }
    
    private List<SecurityEvent> generateSiemEvents(int count) {
        List<SecurityEvent> events = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            SecurityEvent event = new SecurityEvent();
            event.setEventId(UUID.randomUUID().toString());
            event.setEventType("siem");
            event.setTimestamp(getCurrentTimestamp());
            event.setAlertId("SIEM-" + UUID.randomUUID().toString().substring(0, 8));
            event.setDescription("Suspicious activity: " + generateRandomDescription());
            event.setUsername(USERS.get(random.nextInt(USERS.size())));
            event.setSeverity(SEVERITIES.get(random.nextInt(SEVERITIES.size())));
            event.setSourceIp(IPS.get(random.nextInt(IPS.size())));
            event.setDestinationIp(IPS.get(random.nextInt(IPS.size())));
            event.setPort(PORTS.get(random.nextInt(PORTS.size())));
            event.setProtocol(PROTOCOLS.get(random.nextInt(PROTOCOLS.size())));
            event.setAction("none");
            event.setDeviceId("none");
            event.setThreatType("none");
            event.setFilePath("none");
            events.add(event);
        }
        return events;
    }
    
    private List<SecurityEvent> generateEndpointEvents(int count) {
        List<SecurityEvent> events = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            SecurityEvent event = new SecurityEvent();
            event.setEventId(UUID.randomUUID().toString());
            event.setEventType("endpoint");
            event.setTimestamp(getCurrentTimestamp());
            event.setDeviceId("DEV-" + random.nextInt(10000));
            event.setThreatType(THREAT_TYPES.get(random.nextInt(THREAT_TYPES.size())));
            event.setFilePath("/files/" + UUID.randomUUID().toString().substring(0, 8) + ".exe");
            event.setSeverity(SEVERITIES.get(random.nextInt(SEVERITIES.size())));
            event.setSourceIp(IPS.get(random.nextInt(IPS.size())));
            event.setDestinationIp(IPS.get(random.nextInt(IPS.size())));
            event.setPort(PORTS.get(random.nextInt(PORTS.size())));
            event.setProtocol(PROTOCOLS.get(random.nextInt(PROTOCOLS.size())));
            event.setAction("none");
            event.setAlertId("none");
            event.setDescription("none");
            event.setUsername("none");
            events.add(event);
        }
        return events;
    }
    
    private String generateRandomIp() {
        // Fallback if IPS list is empty
        return IPS.get(random.nextInt(IPS.size()));
    }
    
    private String getCurrentTimestamp() {
        return ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
    
    private String generateRandomDescription() {
        String[] descriptions = {
                "Unauthorized login attempt detected",
                "Multiple failed password attempts",
                "Suspicious file download",
                "Potential brute force attack",
                "Unusual network traffic",
                "Configuration change detected",
                "System resource overuse",
                "Potential data exfiltration"
        };
        return descriptions[random.nextInt(descriptions.length)];
    }
    
    @Scheduled(fixedRateString = "${app.events.schedule.interval}")
    public void scheduledGenerateRandomEvents() {
        generateRandomEvents();
    }
    
    @Bean
    public CommandLineRunner initializeEvents() {
        return args -> {
            generateRandomEvents();
        };
    }
}