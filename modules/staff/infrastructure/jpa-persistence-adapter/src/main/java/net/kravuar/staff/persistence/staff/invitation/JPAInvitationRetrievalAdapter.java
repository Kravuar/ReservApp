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
        return invitationRepository.findById(invitationId)
                .orElseThrow(InvitationNotFoundException::new);
    }

    @Override
    public boolean existsActiveByBusinessIdAndSub(long businessId, String sub) {
        return invitationRepository.findByBusinessIdAndSub(businessId, sub)
                .filter(invitation -> invitation.getStatus() == StaffInvitation.Status.WAITING)
                .isPresent();
    }

    @Override
    public List<StaffInvitation> findBySubject(String sub) {
        return invitationRepository.findAllBySub(sub);
    }

    @Override
    public List<StaffInvitation> findByBusinessId(long businessId) {
        return invitationRepository.findAllByBusinessId(businessId);
    }
}
