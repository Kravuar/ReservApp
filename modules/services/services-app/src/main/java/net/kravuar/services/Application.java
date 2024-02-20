package net.kravuar.services;

import net.kravuar.context.AppComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@ComponentScan(
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {AppComponent.class})
        }
)
@ConfigurationPropertiesScan
@Transactional
class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}