package net.kravuar.business.persistence;

import net.kravuar.business.domain.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findByIdAndActive(long businessId, boolean activeOnly);

    Page<Business> findByOwnerSubAndActive(String sub, boolean activeOnly, Pageable pageable);

    Page<Business> findByActiveIsTrue(Pageable pageable);

    boolean existsByNameAndActiveIsTrue(String name);
}
