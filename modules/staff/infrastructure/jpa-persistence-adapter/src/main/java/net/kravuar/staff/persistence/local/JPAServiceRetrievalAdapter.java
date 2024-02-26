package net.kravuar.staff.persistence.local;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.domain.Service;
import net.kravuar.staff.domain.exceptions.ServiceNotFoundException;
import net.kravuar.staff.ports.out.ServiceRetrievalPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JPAServiceRetrievalAdapter implements ServiceRetrievalPort {
    private final ServiceRepository servicesRepository;

    @Override
    public Service findById(long id) {
        return servicesRepository.findById(id)
                .orElseThrow(ServiceNotFoundException::new);
    }
}
