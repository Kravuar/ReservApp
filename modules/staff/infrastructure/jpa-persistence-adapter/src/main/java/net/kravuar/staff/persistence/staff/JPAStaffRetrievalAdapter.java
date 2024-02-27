package net.kravuar.staff.persistence.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.exceptions.StaffNotFoundException;
import net.kravuar.staff.ports.out.StaffRetrievalPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class JPAStaffRetrievalAdapter implements StaffRetrievalPort {
    private final StaffRepository staffRepository;

    @Override
    public Staff findById(long staffId) {
        return staffRepository.findById(staffId)
                .orElseThrow(StaffNotFoundException::new);
    }

    @Override
    public Staff findActiveById(long staffId) {
        return staffRepository.findById(staffId)
                .filter(Staff::isActive)
                .orElseThrow(StaffNotFoundException::new);
    }

    public List<Staff> findAllStaffByBusinessId(long businessId) {
        return staffRepository.findAllByBusinessId(businessId);
    }

    @Override
    public boolean existsActiveByBusinessIdAndSub(long businessId, String sub) {
        return staffRepository.findByBusinessIdAndSub(businessId, sub)
                .filter(Staff::isActive)
                .isPresent();
    }
}
