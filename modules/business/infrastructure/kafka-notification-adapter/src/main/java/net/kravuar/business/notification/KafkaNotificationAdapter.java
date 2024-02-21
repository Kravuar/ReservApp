package net.kravuar.business.notification;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
import net.kravuar.business.ports.out.BusinessNotificationPort;
import net.kravuar.integration.business.BusinessActivityChangeDTO;
import net.kravuar.integration.business.BusinessCreationDTO;
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
                kafkaProps.getTopic(),
                new BusinessCreationDTO(
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
                kafkaProps.getTopic(),
                new BusinessActivityChangeDTO(
                        business.getId(),
                        business.isActive()
                )
        );
    }
}
