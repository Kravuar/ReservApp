package net.kravuar.gateway.graphql.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.dto.ReservationSlotDTO;
import net.kravuar.schedule.dto.ScheduleDTO;
import net.kravuar.services.dto.ServiceDTO;
import net.kravuar.staff.dto.StaffDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
class GraphQLScheduleController {
    private final ScheduleRetrievalClient scheduleRetrievalClient;
    private final ScheduleManagementClient scheduleManagementClient;

    // ================= Properties ================= //

    // ================= Properties ================= //




    // ================= Queries and Mutations ================= //

    @QueryMapping
    Mono<ScheduleDTO> schedule(@Argument long scheduleId, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return scheduleRetrievalClient.byId(scheduleId, requester);
    }

    // ================= Queries and Mutations ================= //




    // ================= Relation from Staff ================= //

    @SchemaMapping(typeName = "Staff")
    Flux<ScheduleDTO> activeSchedules(StaffDTO staff, @Argument long serviceId, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return scheduleRetrievalClient.activeByStaffAndService(staff.id(), serviceId, requester);
    }

    @SchemaMapping(typeName = "Staff")
    Flux<ReservationSlotDTO> schedule(StaffDTO staff, @Argument long serviceId, @Argument LocalDate from, @Argument LocalDate to) {
        return scheduleRetrievalClient.byServiceAndStaff(serviceId, staff.id(), from, to);
    }

    // ================= Relation from Staff ================= //




    // ================= Relation from Service ================= //

    @BatchMapping(typeName = "Service")
    Flux<GroupedFlux<ServiceDTO, ReservationSlotDTO>> schedule(List<ServiceDTO> services, @Argument LocalDate from, @Argument LocalDate to) {
        Map<Long, ServiceDTO> idToService = services.stream()
                .collect(Collectors.toMap(
                        ServiceDTO::id,
                        Function.identity()
                ));

        return scheduleRetrievalClient.byServicesFlat(idToService.keySet(), from, to)
                .groupBy(reservationSlotDTO -> idToService.get(reservationSlotDTO.service().id()));
    }

    @SchemaMapping(typeName = "Service")
    Flux<ReservationSlotDTO> scheduleOfStaff(ServiceDTO service, @Argument long staffId, @Argument LocalDate from, @Argument LocalDate to) {
        return scheduleRetrievalClient.byServiceAndStaff(service.id(), staffId, from, to);
    }

    // ================= Relation from Service ================= //
}