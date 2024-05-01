package net.kravuar.gateway.graphql.schedule;

import net.kravuar.schedule.dto.*;
import net.kravuar.schedule.model.SchedulePattern;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@ReactiveFeignClient(
        name = "scheduleManagement",
        url = "http://schedule:8080/schedule/api-v1/management"
)
interface ScheduleManagementClient {

    @PostMapping("/create/{serviceId}/{staffId}")
    Mono<ScheduleDTO> createSchedule(@PathVariable("serviceId") long serviceId, @PathVariable("staffId") long staffId, @RequestBody ScheduleDetailsDTO details, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @PutMapping("/change/{scheduleId}/patterns")
    Mono<ScheduleDTO> changePatterns(@PathVariable("scheduleId") long scheduleId, @RequestBody List<SchedulePattern> patterns, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @PutMapping("/change/{scheduleId}/duration")
    Mono<ScheduleDTO> changeDuration(@PathVariable("scheduleId") long scheduleId, @RequestBody ScheduleDurationDTO duration, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @PostMapping("/exception-days/create/{serviceId}/{staffId}")
    Mono<ScheduleExceptionDayDTO> createExceptionDay(@PathVariable("serviceId") long serviceId, @PathVariable("staffId") long staffId, @RequestBody ScheduleExceptionDayCreationDTO exceptionDay, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @DeleteMapping("/remove/{scheduleId}")
    Mono<Void> removeSchedule(@PathVariable("scheduleId") long scheduleId, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);
}