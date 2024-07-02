package io.github.arefbehboudi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.github.arefbehboudi")
public class Test {

    public static void main(String[] args) {
        SpringApplication.run(Test.class, args);
    }
}
