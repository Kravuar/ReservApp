package net.kravuar.schedule.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.integration.staff.StaffActivityChangeDTO;
import net.kravuar.integration.staff.StaffCreationDTO;
import net.kravuar.schedule.domain.Staff;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "scheduleLocalStaffUpdates", topics = "${staff.update.staff-update-topic}")
class LocalStaffUpdater {
    private final StaffRepository staffRepository;
    private final BusinessRepository businessRepository;

    @KafkaHandler
    void onStaffCreated(StaffCreationDTO creationDTO) {
        staffRepository.save(
                new Staff(
                        creationDTO.staffId(),
                        businessRepository.getReferenceById(creationDTO.businessId()),
                        creationDTO.active()
                )
        );
    }

    @KafkaHandler
    void onStaffActivityChange(StaffActivityChangeDTO changeDTO) {
        Staff staff = staffRepository.getReferenceById(changeDTO.staffId());
        staff.setActive(changeDTO.active());
        staffRepository.save(staff);
    }
}
