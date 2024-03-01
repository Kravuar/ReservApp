package net.kravuar.staff.notification.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.integration.staff.StaffActivityChangeDTO;
import net.kravuar.integration.staff.StaffCreationDTO;
import net.kravuar.staff.domain.Staff;
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
                new StaffCreationDTO(
                        staff.getId(),
                        staff.getBusiness().getId(),
                        staff.isActive()
                ));
    }

    @Override
    public void notifyStaffActiveChanged(Staff staff) {
        template.send(
                staffKafkaProps.getStaffUpdateTopic(),
                new StaffActivityChangeDTO(
                        staff.getId(),
                        staff.isActive()
                ));
    }
}
