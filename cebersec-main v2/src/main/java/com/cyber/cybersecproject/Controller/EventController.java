package com.cyber.cybersecproject.Controller;

import com.cyber.cybersecproject.Model.ActionResponse;
import com.cyber.cybersecproject.Service.EventGeneratorService;
import com.cyber.cybersecproject.Service.EventProcessorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    
    private final EventProcessorService eventProcessorService;
    private final EventGeneratorService eventGeneratorService;
    
    public EventController(EventProcessorService eventProcessorService,
                           EventGeneratorService eventGeneratorService) {
        this.eventProcessorService = eventProcessorService;
        this.eventGeneratorService = eventGeneratorService;
    }
    
    @GetMapping("/process")
    public List<ActionResponse> processEvents() {
        return eventProcessorService.processEvents();
    }
    
    @GetMapping("/generate")
    public String generateEvents() {
        eventGeneratorService.generateRandomEvents();
        return "Generated and saved events to database.";
    }
}