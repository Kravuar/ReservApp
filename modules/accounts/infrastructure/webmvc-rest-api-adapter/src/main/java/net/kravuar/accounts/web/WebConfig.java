package net.kravuar.accounts.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.dto.DTOAccountMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Configuration
@ControllerAdvice
@RequiredArgsConstructor
class WebConfig {
    @Bean
    DTOAccountMapper dtoMapper() {
        return Mappers.getMapper(DTOAccountMapper.class);
    }
}
