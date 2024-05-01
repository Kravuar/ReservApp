package net.kravuar.schedule.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.schedule.domain.exceptions.StaffNotFoundException;
import net.kravuar.schedule.model.Staff;
import net.kravuar.schedule.ports.out.StaffRetrievalPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JPAStaffRetrievalAdapter implements StaffRetrievalPort {
    private final StaffRepository staffRepository;

    @Override
    public Staff findActiveById(long staffId) {
        return staffRepository.findByIdAndActiveIsTrue(staffId)
                .orElseThrow(StaffNotFoundException::new);
    }
}
