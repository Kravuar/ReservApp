package net.kravuar.schedule.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.integration.services.ServiceActivityChangeDTO;
import net.kravuar.integration.services.ServiceCreationDTO;
import net.kravuar.schedule.domain.Business;
import net.kravuar.schedule.domain.Service;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "service.update.service-update-topic")
@RequiredArgsConstructor
@KafkaListener(id = "scheduleLocalServiceUpdates", topics = "${service.update.service-update-topic}")
class LocalServiceUpdater {
    private final ServiceRepository serviceRepository;
    private final BusinessRepository businessRepository;

    @KafkaHandler
    void onServiceCreated(ServiceCreationDTO creationDTO) {
        Business business = businessRepository.getReferenceById(creationDTO.businessId());
        serviceRepository.save(
                new Service(
                        creationDTO.serviceId(),
                        business,
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
