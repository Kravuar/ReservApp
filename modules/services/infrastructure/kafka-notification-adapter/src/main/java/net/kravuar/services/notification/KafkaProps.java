package net.kravuar.services.notification;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("service.update")
@Data
class KafkaProps {
    private String topic;
}