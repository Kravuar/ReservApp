package net.kravuar.business.persistence;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.ports.out.AccountCheckingPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JPAAccountCheckingAdapter implements AccountCheckingPort {
    private final OwnerRepository ownerRepository;

    @Override
    public boolean hasVerifiedEmail(long accountId) {
        var owner = ownerRepository.findById(accountId);
        return owner.isPresent() && owner.get().isEmailVerified();
    }
}
