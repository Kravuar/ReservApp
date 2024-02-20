package net.kravuar.business.notification;

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
    public NewTopic businessCreationTopic() {
        return TopicBuilder.name(kafkaProps.getBusinessCreationTopic())
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic businessActivityChangedTopic() {
        return TopicBuilder.name(kafkaProps.getBusinessActivityChangedTopic())
                .partitions(1)
                .replicas(1)
                .build();
    }
}