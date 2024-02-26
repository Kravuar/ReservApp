package net.kravuar.staff.notification.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.integration.staff.schedule.ScheduleUpdatedDTO;
import net.kravuar.integration.staff.schedule.WorkingHoursDTO;
import net.kravuar.staff.domain.DailySchedule;
import net.kravuar.staff.ports.out.ScheduleNotificationPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ScheduleKafkaNotificationAdapter implements ScheduleNotificationPort {
    private final ScheduleKafkaProps scheduleKafkaProps;
    private final KafkaTemplate<Object, Object> template;

    @Override
    public void notifyScheduleChange(DailySchedule schedule) {
        this.template.send(
                scheduleKafkaProps.getScheduleChangeTopic(),
                new ScheduleUpdatedDTO(
                        schedule.getDayOfWeek(),
                        schedule.getValidFrom(),
                        schedule.getValidUntil(),
                        schedule.getWorkingHours().stream()
                                .map(workingHours -> new WorkingHoursDTO(
                                                workingHours.getStart(),
                                                workingHours.getEnd(),
                                                workingHours.getCost()
                                        )
                                )
                                .toList()
                )
        );
    }
}
