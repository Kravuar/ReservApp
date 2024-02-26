package net.kravuar.services.persistence.local;

import net.kravuar.services.domain.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BusinessRepository extends JpaRepository<Business, Long> {
}
