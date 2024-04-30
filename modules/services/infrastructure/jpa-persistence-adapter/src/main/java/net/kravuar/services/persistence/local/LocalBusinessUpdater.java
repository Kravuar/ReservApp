package net.kravuar.services.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.dto.BusinessActivityChangeEventDTO;
import net.kravuar.business.dto.BusinessCreationEventDTO;
import net.kravuar.services.model.Business;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "business.update.business-update-topic")
@RequiredArgsConstructor
@KafkaListener(id = "serviceLocalBusinessUpdates", topics = "${business.update.business-update-topic}")
public class LocalBusinessUpdater {
    private final BusinessRepository businessRepository;

    @KafkaHandler
    void onBusinessCreated(BusinessCreationEventDTO creationDTO) {
        businessRepository.save(
                new Business(
                        creationDTO.businessId(),
                        creationDTO.ownerSub(),
                        creationDTO.active()
                )
        );
    }

    @KafkaHandler
    void onBusinessActivityChange(BusinessActivityChangeEventDTO changeDTO) {
        Business business = businessRepository.getReferenceById(changeDTO.businessId());
        business.setActive(changeDTO.active());
        businessRepository.save(business);
    }
}
