package io.github.arefbehboudi.service.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;


@Component
public class SlackMessageService implements MessageService {


    private final static String URL = "https://slack.com/api/chat.postMessage";


    @Value("${io.github.ui-tools.message.channel:#{null}}")
    private String channel;

    @Value("${io.github.ui-tools.message.slack.key:#{null}}")
    private String key;

    @Override
    public void send(String message) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + key);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("channel", channel);
        requestBody.put("text", message);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(requestBody);

        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

        restTemplate.exchange(
                URL,
                HttpMethod.POST,
                entity,
                String.class
        );
    }
}
