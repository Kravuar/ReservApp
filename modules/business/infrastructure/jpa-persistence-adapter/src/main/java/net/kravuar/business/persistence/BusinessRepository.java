package net.kravuar.business.persistence;

import net.kravuar.business.domain.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BusinessRepository extends JpaRepository<Business, Long> {
    List<Business> findByOwnerSubAndActiveIsTrue(String sub);

    List<Business> findAllByActiveIsTrue();

    boolean existsByName(String name);
}
