package net.kravuar.schedule.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.dto.StaffActivityChangeEventDTO;
import net.kravuar.staff.dto.StaffCreationEventDTO;
import net.kravuar.staff.model.Business;
import net.kravuar.staff.model.Staff;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "staff.update.staff-update-topic")
@RequiredArgsConstructor
@KafkaListener(id = "scheduleLocalStaffUpdates", topics = "${staff.update.staff-update-topic}")
class LocalStaffUpdater {
    private final StaffRepository staffRepository;
    private final BusinessRepository businessRepository;

    @KafkaHandler
    void onStaffCreated(StaffCreationEventDTO creationDTO) {
        Business business = businessRepository.getReferenceById(creationDTO.businessId());
        staffRepository.save(
                new Staff(
                        creationDTO.staffId(),
                        business,
                        creationDTO.sub(),
                        creationDTO.active()
                )
        );
    }

    @KafkaHandler
    void onStaffActivityChange(StaffActivityChangeEventDTO changeDTO) {
        Staff staff = staffRepository.getReferenceById(changeDTO.staffId());
        staff.setActive(changeDTO.active());
        staffRepository.save(staff);
    }
}
