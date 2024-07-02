package io.github.arefbehboudi;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class FlywayResourceSolverConfig extends WebMvcConfigurationSupport {


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/flyway-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/WEB-INF/views/")
                .setCachePeriod(0)
                .resourceChain(false)
                .addResolver(new FlywayResourceSolver());

    }
}
