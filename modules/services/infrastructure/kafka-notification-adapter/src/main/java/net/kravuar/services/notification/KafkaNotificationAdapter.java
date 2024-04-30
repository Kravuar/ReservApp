package net.kravuar.services.notification;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.dto.ServiceActivityChangeEventDTO;
import net.kravuar.services.dto.ServiceCreationEventDTO;
import net.kravuar.services.model.Service;
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
                kafkaProps.getServiceUpdateTopic(),
                new ServiceCreationEventDTO(
                        service.getId(),
                        service.getBusiness().getId(),
                        service.isActive()
                )
        );
    }

    @Override
    public void notifyServiceActiveChanged(Service service) {
        this.template.send(
                kafkaProps.getServiceUpdateTopic(),
                new ServiceActivityChangeEventDTO(
                        service.getId(),
                        service.isActive()
                )
        );
    }
}
