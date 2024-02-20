package net.kravuar.services.notification;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("services.notification")
@Data
class KafkaProps {
    private String serviceActivityChangedTopic;
    private String serviceCreationTopic;
}