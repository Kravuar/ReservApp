package net.kravuar.staff.persistence.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.model.Staff;
import net.kravuar.staff.ports.out.StaffPersistencePort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JPAStaffPersistenceAdapter implements StaffPersistencePort {
    private final StaffRepository staffRepository;

    @Override
    public Staff save(Staff staff) {
        return staffRepository.save(staff);
    }
}
