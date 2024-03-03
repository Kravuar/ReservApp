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
        return staffRepository.findByService(staffId, activeOnly)
                .orElseThrow(StaffNotFoundException::new);
    }

    @Override
    public List<Staff> findAllByBusiness(long businessId, boolean activeOnly) {
        return staffRepository.findAllByBusiness(businessId, activeOnly);
    }

    @Override
    public boolean existsActiveByBusinessAndSub(long businessId, String sub) {
        return findByBusinessAndSub(
                businessId,
                sub,
                true,
                true
        ).isPresent();
    }

    @Override
    public Optional<Staff> findByBusinessAndSub(long businessId, String sub, boolean activeOnly, boolean activeBusinessOnly) {
        return staffRepository.findByBusinessAndSub(
                businessId,
                sub,
                activeOnly,
                activeBusinessOnly
        );
    }
}
