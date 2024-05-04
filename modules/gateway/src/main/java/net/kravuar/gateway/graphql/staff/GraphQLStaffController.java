package net.kravuar.gateway.graphql.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.dto.BusinessDTO;
import net.kravuar.pageable.Page;
import net.kravuar.schedule.dto.ReservationDTO;
import net.kravuar.schedule.dto.ReservationDetailedDTO;
import net.kravuar.schedule.dto.ScheduleDTO;
import net.kravuar.schedule.dto.ScheduleOfStaffDTO;
import net.kravuar.staff.dto.StaffDTO;
import net.kravuar.staff.dto.StaffDetailsDTO;
import net.kravuar.staff.dto.StaffInvitationDTO;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
class GraphQLStaffController {
    private final StaffRetrievalClient staffRetrievalClient;
    private final StaffManagementClient staffManagementClient;

    // ================= Queries and Mutations ================= //

    @QueryMapping
    Mono<StaffDTO> staff(@Argument long staffId) {
        return staffRetrievalClient.byId(staffId);
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

    @SchemaMapping(typeName = "ManageableSchedule")
    Mono<StaffDTO> staff(ScheduleDTO schedule) {
        return staffRetrievalClient.byId(schedule.staff().id());
    }

    // ================= Relation from Schedule ================= //




    // ================= Relation from Schedule ================= //

    @SchemaMapping(typeName = "Reservation")
    Mono<StaffDTO> staff(ReservationDTO reservationDTO) {
        return staffRetrievalClient.byId(reservationDTO.service().id());
    }

    @SchemaMapping(typeName = "ReservationDetailed")
    Mono<StaffDTO> staff(ReservationDetailedDTO reservationDTO) {
        return staffRetrievalClient.byId(reservationDTO.service().id());
    }

    @SchemaMapping(typeName = "ScheduleOfStaff")
    Mono<StaffDTO> staff(ScheduleOfStaffDTO schedule) {
        return staffRetrievalClient.byId(schedule.staff().id());
    }

    // ================= Relation from Schedule ================= //




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
}