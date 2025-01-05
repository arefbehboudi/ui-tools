package io.github.arefbehboudi.service.notification;

import io.github.arefbehboudi.model.MessageTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class MessageServiceFactory {

    private final SlackMessageService slackMessageService;

    public MessageServiceFactory(SlackMessageService slackMessageService) {
        this.slackMessageService = slackMessageService;
    }


    public MessageService getSender(MessageTypeEnum type) {
        switch (type) {
            case SLACK -> {
                return slackMessageService;
            }
        }
        throw new RuntimeException(type + "Unsupported type!");

    }

}
