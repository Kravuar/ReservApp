package net.kravuar.staff.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.integration.business.BusinessActivityChangeDTO;
import net.kravuar.integration.business.BusinessCreationDTO;
import net.kravuar.staff.domain.Business;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "businessUpdates", topics = "${business.update.topic}")
class LocalBusinessUpdater {
    private final BusinessRepository businessRepository;

    @KafkaHandler
    void onBusinessCreated(BusinessCreationDTO creationDTO) {
        businessRepository.save(
                Business.builder()
                        .id(creationDTO.id())
                        .ownerSub(creationDTO.ownerSub())
                        .active(creationDTO.active())
                        .build()
        );
    }

    @KafkaHandler
    void onBusinessActivityChange(BusinessActivityChangeDTO changeDTO) {
        Business business = businessRepository.getReferenceById(changeDTO.id());
        business.setActive(changeDTO.active());
        businessRepository.save(business);
    }
}
