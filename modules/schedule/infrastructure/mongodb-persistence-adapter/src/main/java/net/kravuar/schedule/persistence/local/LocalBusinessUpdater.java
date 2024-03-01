package net.kravuar.schedule.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.integration.business.BusinessActivityChangeDTO;
import net.kravuar.integration.business.BusinessCreationDTO;
import net.kravuar.schedule.domain.Business;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "scheduleLocalBusinessUpdates", topics = "${business.update.business-update-topic}")
class LocalBusinessUpdater {
    private final BusinessRepository businessRepository;

    @KafkaHandler
    void onBusinessCreated(BusinessCreationDTO creationDTO) {
        businessRepository.save(
                new Business(
                        creationDTO.businessId(),
                        creationDTO.ownerSub(),
                        creationDTO.active()
                )
        );
    }

    @KafkaHandler
    void onBusinessActivityChange(BusinessActivityChangeDTO changeDTO) {
        Business business = businessRepository.getReferenceById(changeDTO.businessId());
        business.setActive(changeDTO.active());
        businessRepository.save(business);
    }
}
