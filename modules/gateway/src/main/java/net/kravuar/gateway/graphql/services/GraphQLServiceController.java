package net.kravuar.gateway.graphql.services;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.dto.BusinessDTO;
import net.kravuar.pageable.Page;
import net.kravuar.schedule.dto.ReservationDTO;
import net.kravuar.schedule.dto.ReservationDetailedDTO;
import net.kravuar.schedule.dto.ScheduleDTO;
import net.kravuar.services.domain.commands.ServiceCreationCommand;
import net.kravuar.services.dto.ServiceDTO;
import net.kravuar.services.dto.ServiceDetailsDTO;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

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

    @SchemaMapping(typeName = "ManageableSchedule")
    Mono<ServiceDTO> service(ScheduleDTO schedule, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return serviceRetrievalClient.byId(schedule.service().id(), requester);
    }

    // ================= Relation from Schedule ================= //




    // ================= Relation from Reservation ================= //

    @SchemaMapping(typeName = "Reservation")
    Mono<ServiceDTO> service(ReservationDTO reservationDTO, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return serviceRetrievalClient.byId(reservationDTO.service().id(), requester);
    }

    @SchemaMapping(typeName = "ReservationDetailed")
    Mono<ServiceDTO> service(ReservationDetailedDTO reservationDTO, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return serviceRetrievalClient.byId(reservationDTO.service().id(), requester);
    }

    // ================= Relation from Schedule ================= //




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
}
