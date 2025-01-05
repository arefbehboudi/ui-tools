package io.github.arefbehboudi.controller;

import io.github.arefbehboudi.model.HealthResponse;
import io.github.arefbehboudi.service.AlertService;
import io.github.arefbehboudi.service.HealthStatusService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/health")
public class HealthStatusController {

    private final HealthStatusService healthStatusService;
    private final AlertService alertService;

    public HealthStatusController(HealthStatusService healthStatusService,
                                  AlertService alertService) {
        this.healthStatusService = healthStatusService;
        this.alertService = alertService;
    }

    @GetMapping("/")
    @CrossOrigin(origins = "*")
    public List<HealthResponse> getFlywayConfig() {
        return healthStatusService.getStatus();
    }

    @PostMapping("/alert")
    @CrossOrigin(origins = "*")
    public void createAlert(@RequestBody Map<String, Object> alert) {
        alertService.createAlert(alert);
    }

    @DeleteMapping("/alert")
    @CrossOrigin(origins = "*")
    public void deleteAlert(@RequestBody Map<String, Object> alert) {
        alertService.deleteAlert(alert);
    }

}
