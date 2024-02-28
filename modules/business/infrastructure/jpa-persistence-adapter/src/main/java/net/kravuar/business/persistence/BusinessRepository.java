package net.kravuar.business.persistence;

import net.kravuar.business.domain.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findByIdAndActive(long businessId, boolean activeOnly);

    List<Business> findByOwnerSubAndActive(String sub, boolean activeOnly);

    List<Business> findAllByActiveIsTrue();

    boolean existsByNameAndActiveIsTrue(String name);
}
