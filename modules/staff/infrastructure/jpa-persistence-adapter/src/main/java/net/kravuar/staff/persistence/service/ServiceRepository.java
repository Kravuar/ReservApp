package net.kravuar.staff.persistence.service;

import net.kravuar.staff.domain.Business;
import net.kravuar.staff.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByBusinessAndActiveIsTrue(Business business);
    List<Service> findAllByBusiness(Business business);
    List<Service> findAllByActiveIsTrue();
}
