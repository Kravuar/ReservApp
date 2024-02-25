package net.kravuar.staff.persistence.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.Business;
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
    public Staff findById(long id) {
        return staffRepository.findById(id)
                .orElseThrow(StaffNotFoundException::new);
    }

    @Override
    public List<Staff> findAllStaffByBusiness(Business business) {
        return staffRepository.findAllByBusiness(business);
    }
}
