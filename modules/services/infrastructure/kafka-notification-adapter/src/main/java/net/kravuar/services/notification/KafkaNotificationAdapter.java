package net.kravuar.services.notification;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.domain.Service;
import net.kravuar.services.ports.out.ServiceNotificationPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaNotificationAdapter implements ServiceNotificationPort {
    private final KafkaProps kafkaProps;
    private final KafkaTemplate<Object, Object> template;

    @Override
    public void notifyNewService(Service service) {
        this.template.send(
                kafkaProps.getServiceCreationTopic(),
                new ServiceCreationMessage(
                        service.getId(),
                        service.getBusiness().getId(),
                        service.isActive()
                )
        );
    }

    @Override
    public void notifyServiceActiveChanged(Service service) {
        this.template.send(
                kafkaProps.getServiceActivityChangedTopic(),
                new ServiceActivityChangedMessage(
                        service.getId(),
                        service.isActive()
                )
        );
    }

    record ServiceCreationMessage(
            long id,
            long serviceId,
            boolean active
    ) {}

    record ServiceActivityChangedMessage(
            long id,
            boolean active
    ) {}
}
