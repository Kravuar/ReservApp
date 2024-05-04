package net.kravuar.gateway.graphql.schedule;

import net.kravuar.schedule.dto.ScheduleDTO;
import net.kravuar.schedule.dto.ScheduleOfDayDTO;
import net.kravuar.schedule.dto.ScheduleOfStaffDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
@ReactiveFeignClient(
        name = "scheduleRetrieval",
        url = "http://schedule:8085/schedule/api-v1/retrieval"
)
interface ScheduleRetrievalClient {

    @GetMapping("/by-id/{scheduleId}")
    Mono<ScheduleDTO> byId(@PathVariable("scheduleId") long scheduleId, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @GetMapping("/active-by-service-and-staff/{serviceId}/{staffId}")
    Flux<ScheduleDTO> activeByStaffAndService(@PathVariable("staffId") long staffId, @PathVariable("serviceId") long serviceId, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @GetMapping("/by-service/{serviceId}/{from}/{to}")
    Flux<ScheduleOfStaffDTO> byService(@PathVariable("serviceId") long serviceId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to);

    @GetMapping("/by-service/flat/{serviceId}/{from}/{to}")
    Flux<ScheduleOfDayDTO> byServiceFlat(@PathVariable("serviceId") long serviceId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to);

    @GetMapping("/by-service-and-staff/{serviceId}/{staffId}/{from}/{to}")
    Flux<ScheduleOfDayDTO> byServiceAndStaff(@PathVariable("serviceId") long serviceId, @PathVariable("staffId") long staffId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to);
}

