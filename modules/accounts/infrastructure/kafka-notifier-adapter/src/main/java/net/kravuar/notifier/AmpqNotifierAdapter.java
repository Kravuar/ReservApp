package net.kravuar.notifier;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.ports.out.NotificationPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AmpqNotifierAdapter implements NotificationPort {
    private final KafkaTemplate<Object, UpdateDTO> template;
    @Value("${account.notification.email-update-topic:account-email-update-topic}")
    private String emailUpdateTopic;

    @Override
    public void onEmailVerifiedChange(long accountId, boolean verified) {
        this.template.send(emailUpdateTopic, new UpdateDTO(accountId, verified));
    }
}
