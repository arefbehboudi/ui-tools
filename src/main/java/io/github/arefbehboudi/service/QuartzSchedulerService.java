package io.github.arefbehboudi.service;

import io.github.arefbehboudi.model.JobDetailResponse;
import io.github.arefbehboudi.model.SchedulerResponse;
import io.github.arefbehboudi.model.TriggerDetailResponse;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuartzSchedulerService {


    @Autowired(required = false)
    private Scheduler scheduler;

    public SchedulerResponse getSchedulerDetails() {
        try {
            List<JobDetailResponse> jobs = new ArrayList<>();
            List<TriggerDetailResponse> triggers = new ArrayList<>();

            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    List<? extends Trigger> jobTriggers = scheduler.getTriggersOfJob(jobKey);
                    for (Trigger trigger : jobTriggers) {
                        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                        JobDetailResponse jobDetailResponse = getJobDetailResponse(jobKey, trigger, triggerState);
                        jobs.add(jobDetailResponse);

                        TriggerDetailResponse triggerDetailResponse = getTriggerDetailResponse(jobKey, trigger, triggerState);
                        triggers.add(triggerDetailResponse);
                    }
                }
            }

            return new SchedulerResponse(jobs, triggers);
        } catch (Exception e) {
            return null;
        }

    }

    private static TriggerDetailResponse getTriggerDetailResponse(JobKey jobKey, Trigger trigger, Trigger.TriggerState triggerState) {
        TriggerDetailResponse triggerDetailResponse = new TriggerDetailResponse();
        triggerDetailResponse.setName(trigger.getKey().getName());
        triggerDetailResponse.setJobName(jobKey.getName());
        triggerDetailResponse.setStatus(triggerState.name());
        triggerDetailResponse.setNextFireTime(trigger.getNextFireTime() != null ? trigger.getNextFireTime().getTime() : -1);
        triggerDetailResponse.setPreviousFireTime(trigger.getPreviousFireTime() != null ? trigger.getPreviousFireTime().getTime() : -1);
        return triggerDetailResponse;
    }

    private static JobDetailResponse getJobDetailResponse(JobKey jobKey, Trigger trigger, Trigger.TriggerState triggerState) {
        JobDetailResponse jobDetailResponse = new JobDetailResponse();
        jobDetailResponse.setName(jobKey.getName());
        jobDetailResponse.setTrigger(trigger.getKey().getName());
        jobDetailResponse.setStatus(triggerState.name());
        jobDetailResponse.setNextFireTime(trigger.getNextFireTime() != null ? trigger.getNextFireTime().getTime() : -1);
        jobDetailResponse.setPreviousFireTime(trigger.getPreviousFireTime() != null ? trigger.getPreviousFireTime().getTime() : -1);
        return jobDetailResponse;
    }
}
