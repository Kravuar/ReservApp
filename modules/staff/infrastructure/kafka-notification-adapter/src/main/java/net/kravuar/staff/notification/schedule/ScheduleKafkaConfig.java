package net.kravuar.staff.notification.schedule;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
class ScheduleKafkaConfig {
    private final ScheduleKafkaProps scheduleKafkaProps;

    @Bean
    public NewTopic scheduleUpdateTopic() {
        return TopicBuilder.name(scheduleKafkaProps.getScheduleChangeTopic())
                .partitions(1)
                .replicas(1)
                .build();
    }
}