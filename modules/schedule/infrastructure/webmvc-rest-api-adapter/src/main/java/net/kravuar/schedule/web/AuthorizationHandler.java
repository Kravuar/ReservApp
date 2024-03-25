package net.kravuar.schedule.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.Reservation;
import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.Service;
import net.kravuar.schedule.ports.in.ReservationRetrievalUseCase;
import net.kravuar.schedule.ports.in.ScheduleRetrievalUseCase;
import net.kravuar.schedule.ports.in.ServiceRetrievalUseCase;
import net.kravuar.schedule.ports.in.StaffRetrievalUseCase;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AuthorizationHandler {
    private final ScheduleRetrievalUseCase scheduleRetrieval;
    private final ServiceRetrievalUseCase serviceRetrieval;
    private final ReservationRetrievalUseCase reservationRetrieval;
    private final StaffRetrievalUseCase staffRetrieval;

    public boolean isOwnerOfScheduleBusiness(long scheduleId, String subject) {
        Schedule schedule = scheduleRetrieval.findScheduleById(scheduleId, false);
        return schedule
                .getService()
                .getBusiness()
                .getOwnerSub()
                .equals(subject);
    }

    public boolean isOwnerOfServiceBusiness(long serviceId, String subject) {
        Service service = serviceRetrieval.findActiveById(serviceId);
        return service
                .getBusiness()
                .getOwnerSub()
                .equals(subject);
    }

    public boolean isClientOrStaff(long reservationId, String subject) {
        Reservation reservation = reservationRetrieval.findById(reservationId);
        return reservation.getClientSub().equals(subject) || reservation.getStaff().getSub().equals(subject);
    }

    public boolean isStaff(long staffId, String subject) {
        return staffRetrieval.findById(staffId).getSub().equals(subject);
    }
}
