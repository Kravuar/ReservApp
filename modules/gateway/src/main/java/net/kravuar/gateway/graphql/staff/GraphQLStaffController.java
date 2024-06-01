package net.kravuar.gateway.graphql.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.dto.BusinessDTO;
import net.kravuar.pageable.Page;
import net.kravuar.schedule.dto.ReservationDTO;
import net.kravuar.schedule.dto.ReservationDetailedDTO;
import net.kravuar.schedule.dto.ReservationSlotDTO;
import net.kravuar.schedule.dto.ScheduleDTO;
import net.kravuar.staff.dto.StaffDTO;
import net.kravuar.staff.dto.StaffDetailsDTO;
import net.kravuar.staff.dto.StaffInvitationDTO;
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
class GraphQLStaffController {
    private final StaffRetrievalClient staffRetrievalClient;
    private final StaffManagementClient staffManagementClient;

    // ================= Queries and Mutations ================= //

    @QueryMapping
    Mono<StaffDTO> staff(@Argument long staffId, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return staffRetrievalClient.byId(staffId, requester);
    }

    @QueryMapping
    Mono<Page<StaffInvitationDTO>> myInvitations(@Argument long page, @Argument long pageSize, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return staffRetrievalClient.myInvitations(page, pageSize, requester);
    }

    @MutationMapping
    Mono<StaffInvitationDTO> inviteStaff(@Argument String userSub, @Argument long businessId, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return staffManagementClient.sendInvitation(userSub, businessId, requester);
    }

    @MutationMapping
    Mono<StaffDTO> acceptInvitation(@Argument long invitationId, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return staffManagementClient.acceptInvitation(invitationId, requester);
    }

    @MutationMapping
    Mono<StaffInvitationDTO> declineInvitation(@Argument long invitationId, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return staffManagementClient.declineInvitation(invitationId, requester);
    }

    @MutationMapping
    Mono<StaffDTO> changeStaffDetails(@Argument long staffId, @Argument StaffDetailsDTO input, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return staffManagementClient.updateDetails(staffId, input, requester);
    }

    @MutationMapping
    Mono<StaffDTO> removeStaff(@Argument long staffId, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return staffManagementClient.removeStaff(staffId, requester);
    }

    // ================= Queries and Mutations ================= //




    // ================= Relation from Schedule ================= //

    @BatchMapping(typeName = "ManageableSchedule", field = "staff")
    Mono<Map<ScheduleDTO, StaffDTO>> staffBySchedules(List<ScheduleDTO> schedules, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String requester) {
        return mapEntitiesToRelatedData(
                schedules,
                StaffDTO::id,
                schedule -> schedule.staff().id(),
                ids -> staffRetrievalClient.byIds(ids, requester)
        );
    }

    // ================= Relation from Schedule ================= //




    // ================= Relation from Reservation ================= //

    @BatchMapping(typeName = "Reservation", field = "staff")
    Mono<Map<ReservationDTO, StaffDTO>> staffByReservations(List<ReservationDTO> reservations, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String requester) {
        return mapEntitiesToRelatedData(
                reservations,
                StaffDTO::id,
                reservation -> reservation.staff().id(),
                ids -> staffRetrievalClient.byIds(ids, requester)
        );
    }

    @BatchMapping(typeName = "ReservationDetailed", field = "staff")
    Mono<Map<ReservationDetailedDTO, StaffDTO>> staffByReservationsDetailed(List<ReservationDetailedDTO> reservations, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String requester) {
        return mapEntitiesToRelatedData(
                reservations,
                StaffDTO::id,
                reservationDetailed -> reservationDetailed.staff().id(),
                ids -> staffRetrievalClient.byIds(ids, requester)
        );
    }

    // ================= Relation from Reservation ================= //




    // ================= Relation from Business ================= //

    @SchemaMapping(typeName = "Business")
    Mono<Page<StaffDTO>> staff(BusinessDTO business, @Argument long page, @Argument long pageSize) {
        return staffRetrievalClient.findByBusiness(business.id(), page, pageSize);
    }

    @SchemaMapping(typeName = "Business")
    Mono<Page<StaffInvitationDTO>> staffInvitations(BusinessDTO business, @Argument long page, @Argument long pageSize, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return staffRetrievalClient.findInvitationsByBusiness(business.id(), page, pageSize, requester);
    }

    // ================= Relation from Business ================= //




    // ================= Relations from ReservationSlot ================= //

    @BatchMapping(typeName = "ReservationSlot", field = "staff")
    Mono<Map<ReservationSlotDTO, StaffDTO>> staffByReservationSlots(List<ReservationSlotDTO> slots, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String requester) {
        return mapEntitiesToRelatedData(
                slots,
                StaffDTO::id,
                slot -> slot.staff().id(),
                ids -> staffRetrievalClient.byIds(ids, requester)
        );
    }

    // ================= Relations from ReservationSlot ================= //
}