package net.kravuar.gateway.graphql.reservations;

import net.kravuar.schedule.dto.ReservationDetailedDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@ReactiveFeignClient(
        name = "reservationManagement",
        url = "http://schedule:8085/schedule/api-v1/reservation/management"
)
interface ReservationManagementClient {

    @PostMapping("/reserve/{staffId}/{serviceId}/{dateTime}")
    Mono<ReservationDetailedDTO> reserve(@PathVariable("staffId") long staffId, @PathVariable("serviceId") long serviceId, @PathVariable("dateTime") LocalDateTime dateTime, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @DeleteMapping("/cancel/{reservationId}")
    Mono<ReservationDetailedDTO> cancelReservation(@PathVariable("reservationId") long reservationId, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @PostMapping("/restore/{reservationId}")
    Mono<ReservationDetailedDTO> restoreReservation(@PathVariable("reservationId") long reservationId, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);
}