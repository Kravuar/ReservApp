package net.kravuar.services.notification;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
class KafkaConfig {
    private final KafkaProps kafkaProps;

    @Bean
    public NewTopic serviceCreationTopic() {
        return TopicBuilder.name(kafkaProps.getServiceCreationTopic())
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic serviceActivityChangedTopic() {
        return TopicBuilder.name(kafkaProps.getServiceActivityChangedTopic())
                .partitions(1)
                .replicas(1)
                .build();
    }
}