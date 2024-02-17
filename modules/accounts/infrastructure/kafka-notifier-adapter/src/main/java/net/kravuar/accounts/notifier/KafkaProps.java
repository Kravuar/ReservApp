package net.kravuar.accounts.notifier;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("account.notification")
@Data
class KafkaProps {
    private String emailUpdateTopic;
    private String accountCreateTopic;
}
