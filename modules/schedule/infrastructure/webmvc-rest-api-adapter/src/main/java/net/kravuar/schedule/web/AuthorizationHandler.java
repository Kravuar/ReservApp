package net.kravuar.schedule.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.Schedule;
import net.kravuar.schedule.domain.Service;
import net.kravuar.schedule.ports.in.ScheduleRetrievalUseCase;
import net.kravuar.schedule.ports.in.ServiceRetrievalUseCase;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AuthorizationHandler {
    private final ScheduleRetrievalUseCase scheduleRetrieval;
    private final ServiceRetrievalUseCase serviceRetrieval;

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
}
