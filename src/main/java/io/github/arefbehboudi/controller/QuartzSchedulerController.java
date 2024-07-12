package io.github.arefbehboudi.controller;

import io.github.arefbehboudi.model.SchedulerResponse;
import io.github.arefbehboudi.service.QuartzSchedulerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/quartz-scheduler")
public class QuartzSchedulerController {

    private final QuartzSchedulerService quartzSchedulerService;

    public QuartzSchedulerController(QuartzSchedulerService quartzSchedulerService) {
        this.quartzSchedulerService = quartzSchedulerService;
    }

    @GetMapping("/")
    public SchedulerResponse getSchedulerDetails() {
        return quartzSchedulerService.getSchedulerDetails();
    }
}