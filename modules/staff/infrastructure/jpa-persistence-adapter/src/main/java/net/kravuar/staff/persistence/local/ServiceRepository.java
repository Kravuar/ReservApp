package net.kravuar.staff.persistence.local;

import net.kravuar.staff.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ServiceRepository extends JpaRepository<Service, Long> {
}
