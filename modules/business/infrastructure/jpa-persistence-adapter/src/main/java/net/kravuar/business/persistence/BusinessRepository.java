package net.kravuar.business.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BusinessRepository extends JpaRepository<BusinessModel, Long> {
    List<BusinessModel> findByOwnerSubAndActiveIsTrue(String sub);

    List<BusinessModel> findAllByActiveIsTrue();
}
