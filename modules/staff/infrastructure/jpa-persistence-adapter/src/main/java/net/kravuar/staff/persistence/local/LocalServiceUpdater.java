package net.kravuar.staff.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.integration.services.ServiceActivityChangeDTO;
import net.kravuar.integration.services.ServiceCreationDTO;
import net.kravuar.staff.domain.Business;
import net.kravuar.staff.domain.Service;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "serviceUpdates", topics = "${service.update.topic}")
class LocalServiceUpdater {
    private final ServiceRepository serviceRepository;
    private final BusinessRepository businessRepository;

    @KafkaHandler
    void onServiceCreated(ServiceCreationDTO creationDTO) {
        Business business = businessRepository.getReferenceById(creationDTO.id());
        serviceRepository.save(
                Service.builder()
                        .id(creationDTO.id())
                        .business(business)
                        .active(creationDTO.active())
                        .build()
        );
    }

    @KafkaHandler
    void onServiceActivityChange(ServiceActivityChangeDTO changeDTO) {
        Service service = serviceRepository.getReferenceById(changeDTO.id());
        service.setActive(changeDTO.active());
        serviceRepository.save(service);
    }
}
