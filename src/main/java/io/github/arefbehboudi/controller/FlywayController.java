package io.github.arefbehboudi.controller;


import io.github.arefbehboudi.service.FlywayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/migrate")
    public Map<String, Object> migrate() {
        return flywayService.migrate();
    }

    @PostMapping("/undo")
    public Map<String, Object> undo() {
        return flywayService.undo();
    }

    @PostMapping("/clean")
    public Map<String, Object> clean() {
        return flywayService.clean();
    }

    @PostMapping("/validate")
    public Map<String, Object> validate() {
        return flywayService.validate();
    }

    @PostMapping("/repair")
    public Map<String, Object> repair() {
        return flywayService.repair();
    }

}
