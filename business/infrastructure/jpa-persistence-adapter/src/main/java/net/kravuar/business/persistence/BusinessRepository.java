package net.kravuar.business.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface BusinessRepository extends JpaRepository<BusinessModel, Long> {
    Optional<BusinessModel> findByEmail(String email);
    boolean existsByEmail(String email);
}
