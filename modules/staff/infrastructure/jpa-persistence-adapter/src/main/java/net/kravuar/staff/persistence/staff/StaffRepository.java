package net.kravuar.staff.persistence.staff;

import net.kravuar.staff.domain.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query("SELECT s FROM Staff s " +
            "WHERE s.id = :staffId " +
            "AND (:activeOnly = false OR s.active = true) " +
            "AND s.business.active = true")
    Optional<Staff> findById(@Param("staffId") long staffId, @Param("activeOnly") boolean activeOnly);

    @Query("SELECT s FROM Staff s " +
            "WHERE s.business.id = :businessId " +
            "AND (:activeOnly = false OR s.active = true) " +
            "AND s.business.active = true")
    Page<Staff> findByBusiness(@Param("businessId") long businessId, @Param("activeOnly") boolean activeOnly, Pageable pageable);

    @Query("SELECT s FROM Staff s " +
            "WHERE s.business.id = :businessId " +
            "AND s.sub = :sub " +
            "AND (:activeOnly = false OR s.active = true) " +
            "AND (:activeBusinessOnly = false or s.business.active = true)")
    Optional<Staff> findByBusinessAndSub(@Param("businessId") long businessId, @Param("sub") String sub, @Param("activeOnly") boolean activeOnly, @Param("activeBusinessOnly") boolean activeBusinessOnly);
}
