package net.kravuar.schedule.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.integration.staff.StaffActivityChangeDTO;
import net.kravuar.integration.staff.StaffCreationDTO;
import net.kravuar.schedule.domain.Business;
import net.kravuar.schedule.domain.Staff;
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
    void onStaffCreated(StaffCreationDTO creationDTO) {
        Business business = businessRepository.getReferenceById(creationDTO.businessId());
        staffRepository.save(
                new Staff(
                        creationDTO.staffId(),
                        business,
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
