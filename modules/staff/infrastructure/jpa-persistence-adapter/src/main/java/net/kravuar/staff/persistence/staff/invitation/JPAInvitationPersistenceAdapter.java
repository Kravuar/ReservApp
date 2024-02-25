package net.kravuar.staff.persistence.staff.invitation;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.StaffInvitation;
import net.kravuar.staff.ports.out.InvitationPersistencePort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JPAInvitationPersistenceAdapter implements InvitationPersistencePort {
    private final InvitationRepository invitationRepository;

    @Override
    public StaffInvitation save(StaffInvitation invitation) {
        return invitationRepository.save(invitation);
    }
}
