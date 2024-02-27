package net.kravuar.services.persistence.service;

import net.kravuar.services.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByBusinessIdAndActiveIsTrue(long businessId);
    List<Service> findAllByBusinessId(long businessId);
    List<Service> findAllByActiveIsTrue();
    boolean existsByName(String name);
}
