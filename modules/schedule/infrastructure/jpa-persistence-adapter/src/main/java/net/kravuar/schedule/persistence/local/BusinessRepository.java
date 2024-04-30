package net.kravuar.schedule.persistence.local;

import net.kravuar.staff.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findByIdAndActiveIsTrue(long businessId);
}
