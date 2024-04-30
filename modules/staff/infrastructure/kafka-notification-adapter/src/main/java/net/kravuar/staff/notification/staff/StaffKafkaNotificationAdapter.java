package net.kravuar.staff.notification.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.dto.StaffActivityChangeEventDTO;
import net.kravuar.staff.dto.StaffCreationEventDTO;
import net.kravuar.staff.model.Staff;
import net.kravuar.staff.ports.out.StaffNotificationPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class StaffKafkaNotificationAdapter implements StaffNotificationPort {
    private final StaffKafkaProps staffKafkaProps;
    private final KafkaTemplate<Object, Object> template;

    @Override
    public void notifyNewStaff(Staff staff) {
        template.send(
                staffKafkaProps.getStaffUpdateTopic(),
                new StaffCreationEventDTO(
                        staff.getId(),
                        staff.getBusiness().getId(),
                        staff.getSub(),
                        staff.isActive()
                ));
    }

    @Override
    public void notifyStaffActiveChanged(Staff staff) {
        template.send(
                staffKafkaProps.getStaffUpdateTopic(),
                new StaffActivityChangeEventDTO(
                        staff.getId(),
                        staff.isActive()
                ));
    }
}
