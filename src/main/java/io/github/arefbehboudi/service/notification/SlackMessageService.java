package io.github.arefbehboudi.service.notification;

import com.slack.api.Slack;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class SlackMessageService implements MessageService {

    private final Slack slack = Slack.getInstance();

    @Value("${io.github.ui-tools.message.channel:#{null}}")
    private String channel;

    @Value("${io.github.ui-tools.message.slack.key:#{null}}")
    private String key;

    @Override
    public void send(String message) throws Exception {
        slack.methods(key)
                .chatPostMessage(req -> req
                .channel(channel)
                .text(message));
    }
}
