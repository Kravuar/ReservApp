package net.kravuar.staff.persistence.staff.invitation;

import lombok.RequiredArgsConstructor;
import net.kravuar.pageable.Page;
import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.domain.exceptions.InvitationNotFoundException;
import net.kravuar.staff.ports.out.InvitationRetrievalPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

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
    public Page<StaffInvitation> findBySubject(String sub, int page, int pageSize) {
        var invitations = invitationRepository.findBySub(sub, PageRequest.of(page, pageSize));
        return new Page<>(
                invitations.getContent(),
                invitations.getTotalPages()
        );
    }

    @Override
    public Page<StaffInvitation> findByBusiness(long businessId, int page, int pageSize) {
        var invitations = invitationRepository.findByBusiness(businessId, PageRequest.of(page, pageSize));
        return new Page<>(
                invitations.getContent(),
                invitations.getTotalPages()
        );
    }
}
