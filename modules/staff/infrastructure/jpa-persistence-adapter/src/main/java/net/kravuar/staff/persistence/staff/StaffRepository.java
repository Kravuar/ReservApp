package net.kravuar.staff.persistence.staff;

import net.kravuar.staff.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query("SELECT s FROM Staff s " +
            "WHERE s.service.id = :serviceId " +
            "AND s.active = :activeOnly " +
            "AND s.service.active = true " +
            "AND s.service.business.active = true")
    Optional<Staff> findByService(long serviceId, boolean activeOnly);

    @Query("SELECT s FROM Staff s " +
            "WHERE s.service.business.id = :businessId " +
            "AND s.active = :activeOnly " +
            "AND s.service.active = true " +
            "AND s.service.business.active = true")
    List<Staff> findAllByBusiness(long businessId, boolean activeOnly);

    @Query("SELECT s FROM Staff s " +
            "WHERE s.service.business.id = :businessId " +
            "AND s.sub = :sub " +
            "AND s.active = :activeOnly " +
            "AND s.service.active = true " +
            "AND s.service.business.active = :activeBusinessOnly")
    Optional<Staff> findByBusinessAndSub(long businessId, String sub, boolean activeOnly, boolean activeBusinessOnly);
}
