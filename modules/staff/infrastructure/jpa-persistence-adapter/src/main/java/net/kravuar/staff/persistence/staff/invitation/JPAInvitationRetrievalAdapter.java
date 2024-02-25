package net.kravuar.staff.persistence.staff.invitation;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.Business;
import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.domain.exceptions.InvitationNotFoundException;
import net.kravuar.staff.ports.out.InvitationRetrievalPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class JPAInvitationRetrievalAdapter implements InvitationRetrievalPort {
    private final InvitationRepository invitationRepository;

    @Override
    public StaffInvitation findById(long invitationId) {
        return invitationRepository.findById(invitationId)
                .orElseThrow(InvitationNotFoundException::new);
    }

    @Override
    public boolean existsByBusinessAndSub(Business business, String sub) {
        return invitationRepository.existsByBusinessAndSub(business, sub);
    }

    @Override
    public List<StaffInvitation> findBySubject(String sub) {
        return invitationRepository.findAllBySub(sub);
    }

    @Override
    public List<StaffInvitation> findByBusiness(Business business) {
        return invitationRepository.findAllByBusiness(business);
    }
}
