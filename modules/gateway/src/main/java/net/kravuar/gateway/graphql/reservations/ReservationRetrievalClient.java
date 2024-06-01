package net.kravuar.gateway.graphql.reservations;

import net.kravuar.schedule.dto.ReservationDTO;
import net.kravuar.schedule.dto.ReservationDetailedDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@ReactiveFeignClient(
        name = "reservationRetrieval",
        url = "http://schedule:8085/schedule/api-v1/reservation/retrieval"
)
interface ReservationRetrievalClient {

    @GetMapping("/by-service-and-staff/{serviceId}/{staffId}/{from}/{to}")
    Flux<ReservationDTO> byServiceAndStaff(@PathVariable("serviceId") long serviceId, @PathVariable("staffId") long staffId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to);

    @GetMapping("/by-service/{serviceId}/{from}/{to}")
    Flux<ReservationDTO> byService(@PathVariable("serviceId") long serviceId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to);

    @GetMapping("/by-service/detailed/{serviceId}/{from}/{to}")
    Flux<ReservationDetailedDTO> byServiceDetailed(@PathVariable("serviceId") long serviceId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @GetMapping("/by-staff/{staffId}/{from}/{to}")
    Flux<ReservationDTO> byStaff(@PathVariable("staffId") long staffId, @PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to);

    @GetMapping("/my/{from}/{to}")
    Flux<ReservationDetailedDTO> myReservations(@PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @GetMapping("/to-me/{from}/{to}")
    Flux<ReservationDetailedDTO> toMeReservations(@PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);

    @GetMapping("/by-slot/{date}/{start}/{serviceId}/{staffId}")
    Flux<ReservationDTO> bySlot(@PathVariable("date") LocalDate date, @PathVariable("start") LocalTime start, @PathVariable("staffId") long staffId, @PathVariable("serviceId") long serviceId);

    @GetMapping("/by-slot/detailed/{date}/{start}/{serviceId}/{staffId}")
    Flux<ReservationDetailedDTO> bySlotDetailed(@PathVariable("date") LocalDate date, @PathVariable("start") LocalTime start, @PathVariable("staffId") long staffId, @PathVariable("serviceId") long serviceId, @RequestHeader(HttpHeaders.AUTHORIZATION) String requester);
}