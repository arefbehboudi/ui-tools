package io.github.arefbehboudi.service.notification;

import io.github.arefbehboudi.model.HealthResponse;
import io.github.arefbehboudi.model.MessageTypeEnum;
import io.github.arefbehboudi.service.HealthStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class NotificationSender {

    @Value("${io.github.ui-tools.message.type:#{null}}")
    private MessageTypeEnum messageType;

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @Autowired
    private HealthStatusService healthStatusService;

    @Autowired
    private MessageServiceFactory messageServiceFactory;

    @EventListener(ApplicationReadyEvent.class)
    public void initSchedule() {
        scheduledExecutorService.scheduleWithFixedDelay(this::checkHealth, 0, 5, TimeUnit.SECONDS);
    }

    public void checkHealth() {

        List<HealthResponse> status = healthStatusService.getStatus();
        for (HealthResponse healthResponse : status) {
            if (healthResponse.getAlert() && healthResponse.getStatus().equals("DOWN")) {
                try {
                    messageServiceFactory.getSender(messageType)
                            .send(":no_entry: " + healthResponse.getName() + " Is Down " + ":no_entry:");
                } catch (Exception e) {
                    //IGNORE
                }
            }
        }
    }

}
