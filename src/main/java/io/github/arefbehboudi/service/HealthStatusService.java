package io.github.arefbehboudi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HealthStatusService {

    @Autowired(required = false)
    private HealthEndpoint healthEndpoint;

    public Map<String, Object> getStatus() {
        return formatHealthDetails(healthEndpoint.health());
    }

    private Map<String, Object> formatHealthDetails(HealthComponent healthComponent) {
        Map<String, Object> details = new HashMap<>();

        if (healthComponent instanceof CompositeHealth composite) {
            for (Map.Entry<String, HealthComponent> entry : composite.getComponents().entrySet()) {
                details.put(entry.getKey(), formatHealthDetails(entry.getValue()));
            }
        } else if (healthComponent instanceof Health health) {
            Map<String, Object> healthDetails = new HashMap<>(health.getDetails());
            healthDetails.put("status", health.getStatus().getCode());

            details.put("details", healthDetails);
        }

        return details;
    }

}
