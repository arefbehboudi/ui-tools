package io.github.arefbehboudi.service;

import io.github.arefbehboudi.model.Alert;
import io.github.arefbehboudi.model.HealthEnum;
import io.github.arefbehboudi.model.HealthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HealthStatusService {


    @Autowired(required = false)
    private HealthEndpoint healthEndpoint;

    @Autowired
    private AlertService alertService;

    public List<HealthResponse> getStatus() {
        return formatHealthDetails(healthEndpoint.health());
    }

    private List<HealthResponse> formatHealthDetails(HealthComponent healthComponent) {
        List<HealthResponse> details = new ArrayList<>();

        SystemHealth systemHealth = (SystemHealth) healthComponent;
        Map<String, HealthComponent> components = systemHealth.getComponents();
        Set<String> keys = components.keySet();

        List<Alert> alerts = alertService.getAlerts();

        for (String key : keys) {
            HealthEnum health = HealthEnum.getByName(key);
            if(health == null)
                continue;
            String name = health.name();

            if(components.get(key) instanceof Health component) {
                boolean hasAlert = alerts
                        .stream()
                        .anyMatch(alert -> alert.getName().equals(name));

                details.add(new HealthResponse(name, component.getStatus().getCode(), hasAlert));

            }
            if(components.get(key) instanceof CompositeHealth component) {
                boolean hasAlert = alerts
                        .stream()
                        .anyMatch(alert -> alert.getName().equals(name));

                details.add(new HealthResponse(name, component.getStatus().getCode(), hasAlert));
            }
        }

        return details;
    }


}
