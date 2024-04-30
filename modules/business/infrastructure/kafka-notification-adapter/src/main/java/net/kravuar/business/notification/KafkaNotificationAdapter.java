package net.kravuar.business.notification;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.dto.BusinessActivityChangeEventDTO;
import net.kravuar.business.dto.BusinessCreationEventDTO;
import net.kravuar.business.model.Business;
import net.kravuar.business.ports.out.BusinessNotificationPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class KafkaNotificationAdapter implements BusinessNotificationPort {
    private final KafkaProps kafkaProps;
    private final KafkaTemplate<Object, Object> template;

    @Override
    public void notifyNewBusiness(Business business) {
        this.template.send(
                kafkaProps.getBusinessUpdateTopic(),
                new BusinessCreationEventDTO(
                        business.getId(),
                        business.getName(),
                        business.getOwnerSub(),
                        business.isActive()
                )
        );
    }

    @Override
    public void notifyBusinessActiveChanged(Business business) {
        this.template.send(
                kafkaProps.getBusinessUpdateTopic(),
                new BusinessActivityChangeEventDTO(
                        business.getId(),
                        business.isActive()
                )
        );
    }
}
