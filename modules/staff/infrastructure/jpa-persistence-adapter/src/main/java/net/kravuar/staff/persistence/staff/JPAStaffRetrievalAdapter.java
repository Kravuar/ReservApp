package net.kravuar.staff.persistence.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.pageable.Page;
import net.kravuar.staff.domain.Staff;
import net.kravuar.staff.domain.exceptions.StaffNotFoundException;
import net.kravuar.staff.ports.out.StaffRetrievalPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class JPAStaffRetrievalAdapter implements StaffRetrievalPort {
    private final StaffRepository staffRepository;

    @Override
    public Staff findById(long staffId, boolean activeOnly) {
        return staffRepository.findById(staffId, activeOnly)
                .orElseThrow(StaffNotFoundException::new);
    }

    @Override
    public Page<Staff> findByBusiness(long businessId, boolean activeOnly, int page, int pageSize) {
        var staff = staffRepository.findByBusiness(
                businessId,
                activeOnly,
                PageRequest.of(page, pageSize)
        );
        return new Page<>(
                staff.getContent(),
                staff.getTotalPages()
        );
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
