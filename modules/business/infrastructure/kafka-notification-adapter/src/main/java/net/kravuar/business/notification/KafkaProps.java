package net.kravuar.business.notification;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("business.notification")
@Data
class KafkaProps {
    private String businessActivityChangedTopic;
    private String businessCreationTopic;
}