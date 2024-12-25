package io.github.arefbehboudi.controller;

import io.github.arefbehboudi.model.DashboardResponse;
import io.github.arefbehboudi.service.DashboardService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }


    @GetMapping("/")
    @CrossOrigin(origins = "*")
    public DashboardResponse getSchedulerDetails() {
        return dashboardService.getData();
    }

}