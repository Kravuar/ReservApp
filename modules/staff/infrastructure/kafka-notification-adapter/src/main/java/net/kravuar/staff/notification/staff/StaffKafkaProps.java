package net.kravuar.staff.notification.staff;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("staff.update")
@Data
class StaffKafkaProps {
    private String staffUpdateTopic;
}