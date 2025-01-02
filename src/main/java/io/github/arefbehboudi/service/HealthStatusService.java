package io.github.arefbehboudi.service;

import io.github.arefbehboudi.model.Alert;
import io.github.arefbehboudi.model.HealthEnum;
import io.github.arefbehboudi.model.HealthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HealthStatusService {

    private static final String ALERT_FILE = System.getProperty("user.dir") + "\\alerts.dat";

    @Autowired(required = false)
    private HealthEndpoint healthEndpoint;

    public List<HealthResponse> getStatus() {
        return formatHealthDetails(healthEndpoint.health());
    }

    private List<HealthResponse> formatHealthDetails(HealthComponent healthComponent) {
        List<HealthResponse> details = new ArrayList<>();

        SystemHealth systemHealth = (SystemHealth) healthComponent;
        Map<String, HealthComponent> components = systemHealth.getComponents();
        Set<String> keys = components.keySet();

        List<Alert> alerts = getAlerts();

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

    public void createAlert(Map<String, Object> body) {
        try {

            String name = body.get("alert").toString();

            File alertFile = new File(ALERT_FILE);

            if (!alertFile.exists())
                alertFile.createNewFile();

            List<Alert> alerts = getAlerts();

            boolean hasAlert = alerts
                    .stream()
                    .anyMatch(alert -> alert.getName().equals(name));

            if(!hasAlert)
                alerts.add(new Alert(name));


            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ALERT_FILE));
            oos.writeObject(alerts);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private List<Alert> getAlerts() {
        List<Alert> alerts = new ArrayList<>();

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ALERT_FILE));
            alerts = (List<Alert>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            //IGNORE
        }
        return alerts;
    }

    public void deleteAlert(Map<String, Object> body) {
        try {

            String name = body.get("alert").toString();

            List<Alert> alerts = getAlerts();

            alerts = alerts
                    .stream()
                    .filter(alert -> !alert.getName().equals(name))
                    .collect(Collectors.toList());

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ALERT_FILE));
            oos.writeObject(alerts);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
