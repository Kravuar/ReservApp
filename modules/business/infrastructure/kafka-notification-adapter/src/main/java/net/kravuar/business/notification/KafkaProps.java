package net.kravuar.business.notification;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("business.update")
@Data
class KafkaProps {
    private String topic;
}