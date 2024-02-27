package net.kravuar.staff.persistence.staff.invitation;

import net.kravuar.staff.domain.StaffInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface InvitationRepository extends JpaRepository<StaffInvitation, Long> {
    Optional<StaffInvitation> findByBusinessIdAndSub(long businessId, String sub);
    List<StaffInvitation> findAllByBusinessId(long businessId);
    List<StaffInvitation> findAllBySub(String sub);
}
