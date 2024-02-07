package net.kravuar.business.repositories;

import net.kravuar.business.model.BusinessModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<BusinessModel, Long> {
}
