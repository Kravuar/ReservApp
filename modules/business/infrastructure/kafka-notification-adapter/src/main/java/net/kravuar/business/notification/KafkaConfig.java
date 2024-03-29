package net.kravuar.business.notification;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
@EnableKafka
class KafkaConfig {
    private final KafkaProps kafkaProps;

    @Bean
    public NewTopic businessUpdateTopic() {
        return TopicBuilder.name(kafkaProps.getBusinessUpdateTopic())
                .partitions(1)
                .replicas(1)
                .build();
    }
}