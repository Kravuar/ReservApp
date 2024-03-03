package net.kravuar.services.persistence.service;

import net.kravuar.services.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface ServiceRepository extends JpaRepository<Service, Long> {
    @Query("SELECT s FROM Service s " +
            "WHERE s.id = :serviceId " +
            "AND s.active = :activeOnly " +
            "AND s.business.active = true")
    Optional<Service> findById(@Param("serviceId") long serviceId, @Param("activeOnly") boolean activeOnly);

    @Query("SELECT s FROM Service s " +
            "WHERE s.business.id = :businessId " +
            "AND s.active = :activeOnly " +
            "AND s.business.active = true")
    List<Service> findAllByBusiness(@Param("businessId") long businessId, @Param("activeOnly") boolean activeOnly);

    @Query("SELECT s FROM Service s " +
            "WHERE s.active = true " +
            "AND s.business.active = true")
    List<Service> findAllFullyActive();
}
