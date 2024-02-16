package net.kravuar.notifier;

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
    public NewTopic accountCreateTopic() {
        return TopicBuilder.name(kafkaProps.getAccountCreateTopic())
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic emailUpdateTopic() {
        return TopicBuilder.name(kafkaProps.getEmailUpdateTopic())
                .partitions(1)
                .replicas(1)
                .build();
    }
}
