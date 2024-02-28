package net.kravuar.staff.persistence.staff;

import net.kravuar.staff.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByIdAndActive(long serviceId, boolean activeOnly);

    List<Staff> findAllByBusinessIdAndActive(long businessId, boolean activeOnly);

    Optional<Staff> findByBusinessIdAndSub(long businessId, String sub);
}
