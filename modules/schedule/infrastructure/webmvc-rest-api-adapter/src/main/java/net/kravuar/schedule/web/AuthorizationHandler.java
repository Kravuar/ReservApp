package net.kravuar.schedule.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.model.Reservation;
import net.kravuar.staff.model.Schedule;
import net.kravuar.staff.model.Service;
import net.kravuar.schedule.ports.in.ReservationRetrievalUseCase;
import net.kravuar.schedule.ports.in.ScheduleRetrievalUseCase;
import net.kravuar.schedule.ports.in.ServiceRetrievalUseCase;
import net.kravuar.schedule.ports.in.StaffRetrievalUseCase;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
class AuthorizationHandler implements PermissionEvaluator {
    private final ScheduleRetrievalUseCase scheduleRetrieval;
    private final ServiceRetrievalUseCase serviceRetrieval;
    private final ReservationRetrievalUseCase reservationRetrieval;
    private final StaffRetrievalUseCase staffRetrieval;

    private final Map<String, Map<String, BiFunction<String, Object, Boolean>>> permissionEvaluators = Map.of(
            "Reservation", Map.of(
                    "Read", (subject, id) -> isClientOrStaff((long) id, subject),
                    "Cancel", (subject, id) -> isClientOrStaff((long) id, subject),
                    "Restore", (subject, id) -> isClientOrStaff((long) id, subject)
            ),
            "ReservationsOfStaff", Map.of(
                    "Read", (subject, id) -> subject.equals(id) || isStaff((long) id, subject)
            ),
            "Schedule", Map.of(
                    "Update", (subject, id) -> isOwnerOfScheduleBusiness((long) id, subject),
                    "ReadDirect", (subject, id) -> isOwnerOfScheduleBusiness((long) id, subject),
                    "Read", (subject, id) -> isOwnerOfScheduleBusiness((long) id, subject),
                    "Create", (subject, id) -> isOwnerOfServiceBusiness((long) id, subject),
                    "Delete", (subject, id) -> isOwnerOfScheduleBusiness((long) id, subject)
            ),
            "ScheduleException", Map.of(
                    "Create", (subject, id) -> isOwnerOfServiceBusiness((long) id, subject),
                    "Read", (subject, id) -> isOwnerOfServiceBusiness((long) id, subject)
            )
    );

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

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        Map<String, BiFunction<String, Object, Boolean>> permissions = permissionEvaluators.get(targetType);
        if (permissions == null)
            return false;
        BiFunction<String, Object, Boolean> evaluator = permissions.get((String) permission);
        if (evaluator == null)
            return false;
        return evaluator.apply(((Jwt) authentication.getPrincipal()).getSubject(), targetId);
    }
}
