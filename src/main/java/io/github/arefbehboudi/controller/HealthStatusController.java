package io.github.arefbehboudi.controller;

import io.github.arefbehboudi.service.HealthStatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/health")
public class HealthStatusController {

    private final HealthStatusService healthStatusService;

    public HealthStatusController(HealthStatusService healthStatusService) {
        this.healthStatusService = healthStatusService;
    }

    @GetMapping("/")
    public Map<String, Object> getFlywayConfig() {
        return healthStatusService.getStatus();
    }

}
