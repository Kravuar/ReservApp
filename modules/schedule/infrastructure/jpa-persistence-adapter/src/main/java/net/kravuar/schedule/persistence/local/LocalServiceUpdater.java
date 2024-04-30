package net.kravuar.schedule.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.dto.ServiceActivityChangeEventDTO;
import net.kravuar.services.dto.ServiceCreationEventDTO;
import net.kravuar.staff.model.Business;
import net.kravuar.staff.model.Service;
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
    void onServiceCreated(ServiceCreationEventDTO creationDTO) {
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
    void onServiceActivityChange(ServiceActivityChangeEventDTO changeDTO) {
        Service service = serviceRepository.getReferenceById(changeDTO.serviceId());
        service.setActive(changeDTO.active());
        serviceRepository.save(service);
    }
}
