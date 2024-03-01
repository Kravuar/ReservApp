package net.kravuar.schedule.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.integration.services.ServiceActivityChangeDTO;
import net.kravuar.integration.services.ServiceCreationDTO;
import net.kravuar.schedule.domain.Service;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "scheduleLocalServiceUpdates", topics = "${service.update.service-update-topic}")
class LocalServiceUpdater {
    private final ServiceRepository serviceRepository;
    private final BusinessRepository businessRepository;

    @KafkaHandler
    void onServiceCreated(ServiceCreationDTO creationDTO) {
        serviceRepository.save(
                new Service(
                        creationDTO.serviceId(),
                        businessRepository.getReferenceById(creationDTO.businessId()),
                        creationDTO.active()
                )
        );
    }

    @KafkaHandler
    void onServiceActivityChange(ServiceActivityChangeDTO changeDTO) {
        Service service = serviceRepository.getReferenceById(changeDTO.serviceId());
        service.setActive(changeDTO.active());
        serviceRepository.save(service);
    }
}
