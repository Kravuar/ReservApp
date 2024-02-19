package net.kravuar.business.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface BusinessRepository extends JpaRepository<BusinessModel, Long> {
    boolean existsByName(String name);

    Optional<BusinessModel> findByOwnerSub(String sub);

    List<BusinessModel> findAllByActiveIsTrue();
}
