package net.kravuar.services.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.ports.in.ServiceRetrievalUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/retrieval")
@RequiredArgsConstructor
class ServiceRetrievalController {
    private final ServiceRetrievalUseCase serviceRetrieval;
    private final DTOServiceMapper dtoServiceMapper;

    @GetMapping("/active-by-business/{businessId}")
    public List<ServiceDTO> activeByCurrentUser(@PathVariable("businessId") long businessId) {
        return serviceRetrieval.findAllActiveByBusiness(businessId).stream()
                .map(dtoServiceMapper::toDTO)
                .toList();
    }

    @GetMapping("/byId/{id}")
    public ServiceDTO byId(@PathVariable("id") long id) {
        return dtoServiceMapper.toDTO(serviceRetrieval.findById(id));
    }

    @GetMapping("/active")
    public List<ServiceDTO> byOwner() {
        return serviceRetrieval.findAllActive().stream()
                .map(dtoServiceMapper::toDTO)
                .toList();
    }
}
