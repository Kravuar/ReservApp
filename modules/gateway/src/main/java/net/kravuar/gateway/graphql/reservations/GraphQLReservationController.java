package net.kravuar.gateway.graphql.reservations;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.dto.ReservationDTO;
import net.kravuar.schedule.dto.ReservationDetailedDTO;
import net.kravuar.schedule.dto.ReservationSlotDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
class GraphQLReservationController {
    private final ReservationRetrievalClient reservationRetrievalClient;
    private final ReservationManagementClient reservationManagementClient;

    // ================= Queries and Mutations ================= //

    @QueryMapping
    Flux<ReservationDetailedDTO> myReservations(@Argument LocalDate from, @Argument LocalDate to, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return reservationRetrievalClient.myReservations(from, to, requester);
    }

    @QueryMapping
    Flux<ReservationDetailedDTO> reservationsToMe(@Argument LocalDate from, @Argument LocalDate to, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return reservationRetrievalClient.toMeReservations(from, to, requester);
    }

    @MutationMapping
    Mono<ReservationDetailedDTO> reserveSlot(@Argument LocalDateTime dateTime, @Argument long staffId, @Argument long serviceId, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return reservationManagementClient.reserve(staffId, serviceId, dateTime, requester);
    }

    @MutationMapping
    Mono<ReservationDetailedDTO> cancelReservation(@Argument long reservationId, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return reservationManagementClient.cancelReservation(reservationId, requester);
    }

    @MutationMapping
    Mono<ReservationDetailedDTO> restoreReservation(@Argument long reservationId, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return reservationManagementClient.restoreReservation(reservationId, requester);
    }

    // ================= Queries and Mutations ================= //




    // ================= Relations from ReservationSlot ================= //

    @SchemaMapping(typeName = "ReservationSlot")
    Mono<Long> reservationsLeft(ReservationSlotDTO reservationSlot) {
        return reservationRetrievalClient.bySlot(
                reservationSlot.date(),
                reservationSlot.start(),
                reservationSlot.service().id(),
                reservationSlot.staff().id()
        ).count().map(reservationsCount ->
                reservationSlot.maxReservations() - reservationsCount
        );
    }

    @SchemaMapping(typeName = "ReservationSlot")
    Flux<ReservationDTO> reservations(ReservationSlotDTO reservationSlot) {
        return reservationRetrievalClient.bySlot(
                reservationSlot.date(),
                reservationSlot.start(),
                reservationSlot.service().id(),
                reservationSlot.staff().id()
        );
    }

    @SchemaMapping(typeName = "ReservationSlot")
    Flux<ReservationDetailedDTO> reservationDetailed(ReservationSlotDTO reservationSlot, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return reservationRetrievalClient.bySlotDetailed(
                reservationSlot.date(),
                reservationSlot.start(),
                reservationSlot.service().id(),
                reservationSlot.staff().id(),
                requester
        );
    }

    // ================= Relations from ReservationSlot ================= //

}