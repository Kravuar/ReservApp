package net.kravuar.schedule.persistence.local;

import net.kravuar.staff.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ServiceRepository extends JpaRepository<Service, Long> {
    @Query("SELECT s FROM Service s " +
            "WHERE s.id = :serviceId " +
            "AND s.active = true " +
            "AND s.business.active = true")
    Optional<Service> findFullyActiveById(@Param("serviceId") long serviceId);
}
