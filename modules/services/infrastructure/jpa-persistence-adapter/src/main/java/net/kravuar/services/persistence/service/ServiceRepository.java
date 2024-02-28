package net.kravuar.services.persistence.service;

import net.kravuar.services.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByIdAndActive(long serviceId, boolean activeOnly);

    List<Service> findAllByBusinessIdAndActive(long businessId, boolean activeOnly);

    List<Service> findAllByActiveIsTrue();

    boolean existsByNameAndActiveIsTrue(String name);
}
