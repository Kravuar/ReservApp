package net.kravuar.business.web;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.exceptions.BusinessException;
import net.kravuar.business.dto.DTOMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.parameters.AnnotationParameterNameDiscoverer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@ControllerAdvice
@RequiredArgsConstructor
class WebConfig {
    private final AuthorizationHandler authorizationHandler;

    @Bean
    DTOMapper dtoMapper() {
        return Mappers.getMapper(DTOMapper.class);
    }

    @ExceptionHandler(BusinessException.class)
    ResponseEntity<String> handleDomainException(BusinessException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    ResponseEntity<String> handleDomainException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Bean
    MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(authorizationHandler);
        handler.setParameterNameDiscoverer(new AnnotationParameterNameDiscoverer(PathVariable.class.getName()));
        return handler;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException cve) {
        List<String> errorMessages = cve.getConstraintViolations()
                .stream()
                .map(violation -> String.format("%s: %s", violation.getMessage(), violation.getInvalidValue()))
                .toList();

        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.exceptionHandling(customizer ->
                customizer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        );
        httpSecurity.oauth2ResourceServer(customizer -> customizer.jwt(Customizer.withDefaults()));
        return httpSecurity.build();
    }
}
