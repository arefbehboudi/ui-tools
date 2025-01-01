package io.github.arefbehboudi.model;


public class JobDetailResponse {

    private String name;

    private String trigger;

    private String status;

    private long nextFireTime;

    private long previousFireTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(long nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public long getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(long previousFireTime) {
        this.previousFireTime = previousFireTime;
    }
}
