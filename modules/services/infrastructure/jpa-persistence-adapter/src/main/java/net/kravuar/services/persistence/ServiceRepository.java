package net.kravuar.services.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ServiceRepository extends JpaRepository<ServiceModel, Long> {
    List<ServiceModel> findByBusinessIdAndActiveIsTrue(long businessId);
    List<ServiceModel> findAllByBusinessId(long businessId);
    List<ServiceModel> findAllByActiveIsTrue();
}
