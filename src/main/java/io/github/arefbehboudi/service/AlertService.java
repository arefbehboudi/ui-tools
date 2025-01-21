package io.github.arefbehboudi.service;

import io.github.arefbehboudi.model.Alert;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private static final String ALERT_FILE = System.getProperty("user.dir") + "\\alerts.dat";


    public void createAlert(Map<String, Object> body) {
        try {

            String name = body.get("alert")
                    .toString();

            File alertFile = new File(ALERT_FILE);

            if (!alertFile.exists()) {
                boolean newFile = alertFile.createNewFile();
                if(newFile)
                    throw new RuntimeException("Cannot create alert file!");
            }

            List<Alert> alerts = getAlerts();

            boolean hasAlert = alerts
                    .stream()
                    .anyMatch(alert -> alert.getName().equals(name));

            if(!hasAlert)
                alerts.add(new Alert(name));


            ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(ALERT_FILE)));
            oos.writeObject(alerts);

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Alert> getAlerts() {
        List<Alert> alerts = new ArrayList<>();

        try {
            ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(ALERT_FILE)));
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
