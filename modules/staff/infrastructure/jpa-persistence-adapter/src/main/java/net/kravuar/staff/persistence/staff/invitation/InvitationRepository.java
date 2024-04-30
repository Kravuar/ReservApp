package net.kravuar.staff.persistence.staff.invitation;

import net.kravuar.staff.model.StaffInvitation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface InvitationRepository extends JpaRepository<StaffInvitation, Long> {
    @Query("SELECT s FROM StaffInvitation s " +
            "WHERE s.business.id = :businessId " +
            "AND s.status = net.kravuar.staff.domain.StaffInvitation$Status.WAITING " +
            "AND s.sub = :sub " +
            "AND s.business.active = true")
    Optional<StaffInvitation> findWaitingByBusinessAndSub(@Param("businessId") long businessId, @Param("sub") String sub);

    @Query("SELECT s FROM StaffInvitation s " +
            "WHERE s.id = :invitationId " +
            "AND s.business.active = true")
    Optional<StaffInvitation> findFullyActiveById(@Param("invitationId") long invitationId);

    @Query("SELECT s FROM StaffInvitation s " +
            "WHERE s.business.id = :businessId " +
            "AND s.business.active = true")
    Page<StaffInvitation> findByBusiness(@Param("businessId") long businessId, Pageable pageable);

    @Query("SELECT s FROM StaffInvitation s " +
            "WHERE s.sub = :sub " +
            "AND s.business.active = true")
    Page<StaffInvitation> findBySub(@Param("sub") String sub, Pageable pageable);
}
