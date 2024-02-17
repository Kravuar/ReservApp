package net.kravuar.accounts.notifier;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.ports.out.NotificationPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaNotifierAdapter implements NotificationPort {
    private final KafkaTemplate<Object, Object> template;
    private final KafkaProps kafkaProps;

    @Override
    public void onEmailVerifiedChange(long accountId, boolean verified) {
        this.template.send(kafkaProps.getEmailUpdateTopic(), new UpdateDTO(accountId, verified));
    }

    @Override
    public void onAccountCreation(long accountId, String username, String email, boolean emailVerified) {
        this.template.send(kafkaProps.getAccountCreateTopic(), new CreateDTO(
                accountId,
                username,
                email,
                emailVerified
        ));
    }
}
