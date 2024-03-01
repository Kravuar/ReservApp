package net.kravuar.staff.notification.staff;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
class StaffKafkaConfig {
    private final StaffKafkaProps staffKafkaProps;

    @Bean
    public NewTopic staffUpdateTopic() {
        return TopicBuilder.name(staffKafkaProps.getStaffUpdateTopic())
                .partitions(1)
                .replicas(1)
                .build();
    }
}