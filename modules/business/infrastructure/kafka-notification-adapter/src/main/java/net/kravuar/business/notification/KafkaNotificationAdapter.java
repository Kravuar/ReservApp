package net.kravuar.business.notification;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.domain.Business;
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
                kafkaProps.getBusinessCreationTopic(),
                new BusinessCreationMessage(
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
                kafkaProps.getBusinessActivityChangedTopic(),
                new BusinessActivityChangedMessage(
                        business.getId(),
                        business.isActive()
                )
        );
    }

    record BusinessCreationMessage(
            long id,
            String name,
            String ownerSub,
            boolean active
    ) {
    }

    record BusinessActivityChangedMessage(
            long id,
            boolean active
    ) {
    }
}
