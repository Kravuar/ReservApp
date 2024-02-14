package net.kravuar.business.persistence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;
import net.kravuar.business.ports.out.AccountCheckingPort;
import org.springframework.kafka.annotation.KafkaListener;
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

    @KafkaListener(id = "account-email-change-sync", topics = "${business.updates.email-update-topic}")
    public void onEmailUpdate(EmailUpdateDTO update) {
        ownerRepository.save(new OwnerModel(
                        update.accountId,
                        update.verified
                )
        );
    }

    @KafkaListener(id = "new-account-change-sync", topics = "${business.updates.account-create-topic}")
    public void onNewAccount(NewAccountDTO update) {
        ownerRepository.save(new OwnerModel(
                        update.accountId,
                        update.emailVerified
                )
        );
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record EmailUpdateDTO(long accountId, boolean verified) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record NewAccountDTO(long accountId, boolean emailVerified) {
    }
}
