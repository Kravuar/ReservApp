package net.kravuar.gateway.graphql.reservation;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.dto.AnonymousReservationsByDayDTO;
import net.kravuar.schedule.dto.ReservationDetailedDTO;
import net.kravuar.schedule.dto.ReservationsByDayDTO;
import net.kravuar.services.dto.ServiceDTO;
import net.kravuar.staff.dto.StaffDTO;
import org.springframework.graphql.data.method.annotation.*;
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
    Flux<ReservationsByDayDTO> myReservations(@Argument LocalDate from, @Argument LocalDate to, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return reservationRetrievalClient.myReservations(from, to, requester);
    }

    @QueryMapping
    Flux<ReservationsByDayDTO> reservationsToMe(@Argument LocalDate from, @Argument LocalDate to, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
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




    // ================= Relation from Staff ================= //

    @SchemaMapping(typeName = "Staff")
    Flux<AnonymousReservationsByDayDTO> anonymousReservationsByService(StaffDTO staff, @Argument long serviceId, @Argument LocalDate from, @Argument LocalDate to) {
        return reservationRetrievalClient.byServiceAndStaff(serviceId, staff.id(), from, to);
    }

    @SchemaMapping(typeName = "Staff")
    Flux<ReservationsByDayDTO> reservations(StaffDTO staff, @Argument LocalDate from, @Argument LocalDate to, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return reservationRetrievalClient.byStaff(staff.id(), from, to, requester);
    }

    // ================= Relation from Staff ================= //




    // ================= Relation from Service ================= //

    @SchemaMapping(typeName = "Service")
    Flux<AnonymousReservationsByDayDTO> anonymousReservationsByStaff(ServiceDTO service, @Argument long staffId, @Argument LocalDate from, @Argument LocalDate to) {
        return reservationRetrievalClient.byServiceAndStaff(service.id(), staffId, from, to);
    }

    // ================= Relation from Service ================= //
}