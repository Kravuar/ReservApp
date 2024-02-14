package net.kravuar.notifier;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.ports.out.NotificationPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AmpqNotifierAdapter implements NotificationPort {
    private final KafkaTemplate<Object, Object> template;
    @Value("${account.notification.email-update-topic}")
    private String emailUpdateTopic;
    @Value("${account.notification.account-create-topic}")
    private String accountCreateTopic;

    @Override
    public void onEmailVerifiedChange(long accountId, boolean verified) {
        this.template.send(emailUpdateTopic, new UpdateDTO(accountId, verified));
    }

    @Override
    public void onAccountCreation(long accountId, String username, String email, boolean emailVerified) {
        this.template.send(accountCreateTopic, new CreateDTO(
                accountId,
                username,
                email,
                emailVerified
        ));
    }
}
