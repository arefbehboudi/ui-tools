package io.github.arefbehboudi.model;

public class HealthResponse {

    private String name;

    private String status;

    private Boolean alert;

    private Integer lastMessageSend;

    public HealthResponse(String name, String status, Boolean alert) {
        this.name = name;
        this.status = status;
        this.alert = alert;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getAlert() {
        return alert;
    }

    public Integer getLastMessageSend() {
        return lastMessageSend;
    }

    public void setLastMessageSend(Integer lastMessageSend) {
        this.lastMessageSend = lastMessageSend;
    }
}
