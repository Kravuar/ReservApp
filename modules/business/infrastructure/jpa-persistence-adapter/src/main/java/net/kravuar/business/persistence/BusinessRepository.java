package net.kravuar.business.persistence;

import net.kravuar.business.model.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
interface BusinessRepository extends JpaRepository<Business, Long> {
    @Query("SELECT b FROM Business b " +
            "WHERE b.id = :businessId " +
            "AND (:activeOnly = false OR b.active = true)")
    Optional<Business> findByIdAndActive(@Param("businessId") long businessId, @Param("activeOnly") boolean activeOnly);

    @Query("SELECT b FROM Business b " +
            "WHERE b.id IN :businessIds " +
            "AND (:activeOnly = false OR b.active = true)")
    List<Business> findByIdsAndActive(@Param("businessIds") Set<Long> businessIds, @Param("activeOnly") boolean activeOnly);

    @Query("SELECT b FROM Business b " +
            "WHERE b.ownerSub = :sub " +
            "AND (:activeOnly = false OR b.active = true)")
    Page<Business> findByOwnerSubAndActive(@Param("sub") String sub, @Param("activeOnly") boolean activeOnly, Pageable pageable);

    Page<Business> findByActiveIsTrue(Pageable pageable);

    boolean existsByNameAndActiveIsTrue(String name);
}
