package net.kravuar.staff.notification.schedule;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("schedule.update")
@Data
class ScheduleKafkaProps {
    private String scheduleChangeTopic;
}