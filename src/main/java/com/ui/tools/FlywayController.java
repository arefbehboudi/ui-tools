package com.ui.tools;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/flyway")
public class FlywayController {
    private final FlywayService flywayService;


    public FlywayController(FlywayService flywayService) {
        this.flywayService = flywayService;
    }


    @GetMapping("/")
    public Map<String, Object> getFlywayConfig() {
        return flywayService.getFlywayData();
    }


}
