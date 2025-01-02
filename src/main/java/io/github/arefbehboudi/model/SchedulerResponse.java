package io.github.arefbehboudi.model;

import java.util.List;

public class SchedulerResponse {

    private List<JobDetailResponse> jobs;

    private List<TriggerDetailResponse> triggers;

    public SchedulerResponse(List<JobDetailResponse> jobs, List<TriggerDetailResponse> triggers) {
        this.jobs = jobs;
        this.triggers = triggers;
    }

    public List<JobDetailResponse> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobDetailResponse> jobs) {
        this.jobs = jobs;
    }

    public List<TriggerDetailResponse> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<TriggerDetailResponse> triggers) {
        this.triggers = triggers;
    }

    // Getters and Setters
}
