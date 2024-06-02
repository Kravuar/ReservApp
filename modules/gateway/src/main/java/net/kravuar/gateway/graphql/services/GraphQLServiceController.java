package net.kravuar.gateway.graphql.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.dto.BusinessDTO;
import net.kravuar.pageable.Page;
import net.kravuar.schedule.dto.ReservationDTO;
import net.kravuar.schedule.dto.ReservationDetailedDTO;
import net.kravuar.schedule.dto.ReservationSlotDTO;
import net.kravuar.schedule.dto.ScheduleDTO;
import net.kravuar.services.domain.commands.ServiceCreationCommand;
import net.kravuar.services.dto.ServiceDTO;
import net.kravuar.services.dto.ServiceDetailsDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static net.kravuar.gateway.graphql.BatchHelper.mapEntitiesToRelatedData;

@Controller
@RequiredArgsConstructor
class GraphQLServiceController {
    private final ServiceRetrievalClient serviceRetrievalClient;
    private final ServiceManagementClient serviceManagementClient;

    // ================= Queries and Mutations ================= //

    @QueryMapping
    Mono<ServiceDTO> service(@Argument long serviceId, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return serviceRetrievalClient.byId(serviceId, requester);
    }

    @QueryMapping
    Mono<Page<ServiceDTO>> services(@Argument int page, @Argument int pageSize) {
        return serviceRetrievalClient.allActive(page, pageSize);
    }

    @MutationMapping
    Mono<ServiceDTO> createService(@Argument ServiceCreationCommand input, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return serviceManagementClient.create(input, requester);
    }

    @MutationMapping
    Mono<ServiceDTO> changeServiceActiveness(@Argument long serviceId, @Argument boolean active, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return serviceManagementClient.changeActive(serviceId, active, requester);
    }

    @MutationMapping
    Mono<ServiceDTO> changeServiceDetails(@Argument long serviceId, @Argument ServiceDetailsDTO input, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return serviceManagementClient.updateDetails(serviceId, input, requester);
    }

    // ================= Queries and Mutations ================= //




    // ================= Relation from Schedule ================= //

    @BatchMapping(typeName = "ManageableSchedule", field = "service")
    Mono<Map<ScheduleDTO, ServiceDTO>> servicesBySchedules(List<ScheduleDTO> schedules, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return mapEntitiesToRelatedData(
                schedules,
                ServiceDTO::id,
                schedule -> schedule.service().id(),
                ids -> serviceRetrievalClient.byIds(ids, requester)
        );
    }

    // ================= Relation from Schedule ================= //




    // ================= Relation from Reservation ================= //

    @BatchMapping(typeName = "Reservation", field = "service")
    Mono<Map<ReservationDTO, ServiceDTO>> servicesByReservations(List<ReservationDTO> reservations, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return mapEntitiesToRelatedData(
                reservations,
                ServiceDTO::id,
                reservation -> reservation.service().id(),
                ids -> serviceRetrievalClient.byIds(ids, requester)
        );
    }

    @BatchMapping(typeName = "ReservationDetailed", field = "service")
    Mono<Map<ReservationDetailedDTO, ServiceDTO>> servicesByReservationsDetailed(List<ReservationDetailedDTO> reservations, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return mapEntitiesToRelatedData(
                reservations,
                ServiceDTO::id,
                reservationDetailed -> reservationDetailed.service().id(),
                ids -> serviceRetrievalClient.byIds(ids, requester)
        );
    }

    // ================= Relation from Reservation ================= //




    // ================= Relation from Business ================= //

    @SchemaMapping(typeName = "Business")
    Mono<Page<ServiceDTO>> myServices(BusinessDTO business, @Argument int page, @Argument int pageSize, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return serviceRetrievalClient.myByBusiness(business.id(), page, pageSize, requester);
    }

    @SchemaMapping(typeName = "Business")
    Mono<Page<ServiceDTO>> services(BusinessDTO business, @Argument int page, @Argument int pageSize) {
        return serviceRetrievalClient.byBusiness(business.id(), page, pageSize);
    }

    // ================= Relation from Business ================= //




    // ================= Relations from ReservationSlot ================= //

    @BatchMapping(typeName = "ReservationSlot", field = "service")
    Mono<Map<ReservationSlotDTO, ServiceDTO>> servicesByIds(List<ReservationSlotDTO> slots, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return mapEntitiesToRelatedData(
                slots,
                ServiceDTO::id,
                slot -> slot.service().id(),
                ids -> serviceRetrievalClient.byIds(ids, requester)
        );
    }

    // ================= Relations from ReservationSlot ================= //
}
