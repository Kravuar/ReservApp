package net.kravuar.gateway.graphql.reservations;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.dto.ReservationDTO;
import net.kravuar.schedule.dto.ReservationDetailedDTO;
import net.kravuar.schedule.dto.ReservationSlotDTO;
import net.kravuar.schedule.dto.StaffDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

//    @BatchMapping(typeName = "ReservationSlot")
//    Mono<Map<ReservationSlotDTO, Integer>> reservationsLeft(List<ReservationSlotDTO> reservationSlots, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
//        if (reservationSlots.isEmpty()) {
//            return Mono.just(Collections.emptyMap());
//        }
//        LocalDate min = reservationSlots.stream().min(Comparator.comparing(ReservationSlotDTO::date)).get().date();
//        LocalDate max = reservationSlots.stream().min(Comparator.comparing(ReservationSlotDTO::date)).get().date();
//
//        return reservationSlots.
//    }
//
//    @BatchMapping(typeName = "ReservationSlot")
//    Flux<Flux<ReservationDetailedDTO>> reservations(List<ReservationSlotDTO> reservationSlots) {
//
//    }
//
//    @BatchMapping(typeName = "ReservationSlot")
//    Flux<Flux<ReservationDetailedDTO>> reservationsDetailed(List<ReservationSlotDTO> reservationSlots, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
//
//    }
//
//    // ================= Relations from ReservationSlot ================= //
//
//    Flux<Flux<ReservationDetailedDTO>> fetchReservationsDetailed(List<ReservationSlotDTO> reservationSlots, String requester) {
//        if (reservationSlots.isEmpty()) {
//            return Flux.empty();
//        }
//        Map<Long, List<ReservationSlotDTO>> slotsByService = reservationSlots.stream()
//                .collect(Collectors.groupingBy(reservationSlot -> reservationSlot.service().id()));
//
//        Flux<GroupedFlux<Long, Flux<ReservationDetailedDTO>>> reservationsByService =  Flux.fromIterable(slotsByService.entrySet())
//                .groupBy(
//                        Map.Entry::getKey,
//                        byServiceEntry -> {
//                    assert !byServiceEntry.getValue().isEmpty();
//                    LocalDate minByService = byServiceEntry.getValue().stream().min(Comparator.comparing(ReservationSlotDTO::date)).get().date();
//                    LocalDate maxByService = byServiceEntry.getValue().stream().min(Comparator.comparing(ReservationSlotDTO::date)).get().date();
//
//                    long serviceId = byServiceEntry.getKey();
//                    return reservationRetrievalClient.byServiceDetailed(serviceId, minByService, maxByService, requester);
//                });
//
//    }
}