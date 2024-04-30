package net.kravuar.schedule.persistence.local;

import net.kravuar.staff.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query("SELECT s FROM Staff s " +
            "WHERE s.id = :staffId " +
            "AND s.active = true " +
            "AND s.business.active = true")
    Optional<Staff> findByIdAndActiveIsTrue(@Param("staffId") long staffId);
}
