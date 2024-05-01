package net.kravuar.gateway.graphql.reservation;

import net.kravuar.schedule.dto.AnonymousReservationsByDayDTO;
import net.kravuar.schedule.dto.ReservationsByDayDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Component
@ReactiveFeignClient(
        name = "reservationRetrieval",
        url = "http://schedule:8080/api-v1/reservation/retrieval"
)
interface ReservationRetrievalClient {

    @GetMapping("/by-service-and-staff/{serviceId}/{staffId}/{from}/{to}")
    Flux<AnonymousReservationsByDayDTO> byServiceAndStaff(@PathVariable("serviceId") long serviceId, @PathVariable("staffId") long staffId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to);

    @GetMapping("/by-staff/{staffId}/{from}/{to}")
    Flux<ReservationsByDayDTO> byStaff(@PathVariable("staffId") long staffId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @GetMapping("/my/{from}/{to}")
    Flux<ReservationsByDayDTO> myReservations(@PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @GetMapping("/to-me/{from}/{to}")
    Flux<ReservationsByDayDTO> toMeReservations(@PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);
}