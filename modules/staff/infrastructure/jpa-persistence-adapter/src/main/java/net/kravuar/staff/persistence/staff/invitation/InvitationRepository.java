package net.kravuar.staff.persistence.staff.invitation;

import net.kravuar.staff.domain.StaffInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface InvitationRepository extends JpaRepository<StaffInvitation, Long> {
    @Query("SELECT s FROM StaffInvitation s " +
            "WHERE s.business.id = :businessId " +
            "AND s.status = net.kravuar.staff.domain.StaffInvitation$Status.WAITING " +
            "AND s.sub = :sub " +
            "AND s.business.active = true")
    Optional<StaffInvitation> findWaitingByBusinessAndSub(long businessId, String sub);

    @Query("SELECT s FROM StaffInvitation s " +
            "WHERE s.id = :invitationId " +
            "AND s.business.active = true")
    Optional<StaffInvitation> findFullyActiveById(long invitationId);

    @Query("SELECT s FROM StaffInvitation s " +
            "WHERE s.business.id = :businessId " +
            "AND s.business.active = true")
    List<StaffInvitation> findAllByBusiness(long businessId);

    @Query("SELECT s FROM StaffInvitation s " +
            "WHERE s.sub = :sub " +
            "AND s.business.active = true")
    List<StaffInvitation> findAllBySub(String sub);
}
