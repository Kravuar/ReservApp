package net.kravuar.schedule;

import net.kravuar.context.AppComponent;
import net.kravuar.context.AppValidated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication
@ComponentScan(
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {AppComponent.class})
        }
)
@ConfigurationPropertiesScan
class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidatedAnnotationType(AppValidated.class);
        return postProcessor;
    }
}