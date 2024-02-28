package net.kravuar.staff.persistence.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.exceptions.StaffNotFoundException;
import net.kravuar.staff.ports.out.StaffRetrievalPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class JPAStaffRetrievalAdapter implements StaffRetrievalPort {
    private final StaffRepository staffRepository;

    @Override
    public Staff findById(long staffId, boolean activeOnly) {
        return staffRepository.findByIdAndActive(staffId, activeOnly)
                .orElseThrow(StaffNotFoundException::new);
    }

    @Override
    public List<Staff> findAllStaffByBusinessId(long businessId, boolean activeOnly) {
        return staffRepository.findAllByBusinessIdAndActive(businessId, activeOnly);
    }

    @Override
    public boolean existsActiveByBusinessIdAndSub(long businessId, String sub) {
        return staffRepository.findByBusinessIdAndSub(businessId, sub)
                .filter(Staff::isActive)
                .isPresent();
    }

    @Override
    public Optional<Staff> findByBusinessIdAndSub(long businessId, String sub, boolean activeOnly) {
        return staffRepository.findByBusinessIdAndSub(businessId, sub);
    }
}
