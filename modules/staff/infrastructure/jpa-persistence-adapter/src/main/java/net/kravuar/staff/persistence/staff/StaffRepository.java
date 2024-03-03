package net.kravuar.staff.persistence.staff;

import net.kravuar.staff.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query("SELECT s FROM Staff s " +
            "WHERE s.id = :staffId " +
            "AND s.active = :activeOnly " +
            "AND s.business.active = true")
    Optional<Staff> findById(@Param("staffId") long staffId, @Param("activeOnly") boolean activeOnly);

    @Query("SELECT s FROM Staff s " +
            "WHERE s.business.id = :businessId " +
            "AND s.active = :activeOnly " +
            "AND s.business.active = true")
    List<Staff> findAllByBusiness(@Param("businessId") long businessId, @Param("activeOnly") boolean activeOnly);

    @Query("SELECT s FROM Staff s " +
            "WHERE s.business.id = :businessId " +
            "AND s.sub = :sub " +
            "AND s.active = :activeOnly " +
            "AND s.business.active = :activeBusinessOnly")
    Optional<Staff> findByBusinessAndSub(@Param("businessId") long businessId, @Param("sub") String sub, @Param("activeOnly") boolean activeOnly, @Param("activeBusinessOnly") boolean activeBusinessOnly);
}
