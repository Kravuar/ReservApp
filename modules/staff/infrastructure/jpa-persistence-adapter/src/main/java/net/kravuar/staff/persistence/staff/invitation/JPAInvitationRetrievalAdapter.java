package net.kravuar.staff.persistence.staff.invitation;

import lombok.RequiredArgsConstructor;
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
        return invitationRepository.findFullyActiveById(invitationId)
                .orElseThrow(InvitationNotFoundException::new);
    }

    @Override
    public boolean existsWaitingByBusinessAndSub(long businessId, String sub) {
        return invitationRepository.findWaitingByBusinessAndSub(businessId, sub)
                .isPresent();
    }

    @Override
    public List<StaffInvitation> findAllBySubject(String sub) {
        return invitationRepository.findAllBySub(sub);
    }

    @Override
    public List<StaffInvitation> findAllByBusiness(long businessId) {
        return invitationRepository.findAllByBusiness(businessId);
    }
}
