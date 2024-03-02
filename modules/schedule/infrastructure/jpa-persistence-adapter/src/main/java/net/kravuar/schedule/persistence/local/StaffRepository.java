package net.kravuar.schedule.persistence.local;

import net.kravuar.schedule.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByIdAndActiveIsTrue(long serviceId);
}
