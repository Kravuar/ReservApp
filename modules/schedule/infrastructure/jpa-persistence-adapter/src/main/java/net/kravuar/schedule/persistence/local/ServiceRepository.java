package net.kravuar.schedule.persistence.local;

import net.kravuar.schedule.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByIdAndActiveIsTrue(long serviceId);
}
