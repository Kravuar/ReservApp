package net.kravuar.staff.persistence.staff.invitation;

import net.kravuar.staff.domain.Business;
import net.kravuar.staff.domain.StaffInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface InvitationRepository extends JpaRepository<StaffInvitation, Long> {
    boolean existsByBusinessAndSub(Business business, String sub);
    List<StaffInvitation> findAllByBusiness(Business business);
    List<StaffInvitation> findAllBySub(String sub);
}
