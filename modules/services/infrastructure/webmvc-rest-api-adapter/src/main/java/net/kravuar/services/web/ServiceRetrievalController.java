package net.kravuar.services.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.services.domain.Service;
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
    private final ServiceRetrievalUseCase serviceRetrievalUseCase;

    @GetMapping("/active-by-business/{businessId}")
    public List<Service> activeByCurrentUser(@PathVariable("businessId") long businessId) {
        return serviceRetrievalUseCase.findAllActiveByBusiness(businessId);
    }

    @GetMapping("/byId/{id}")
    public Service byId(@PathVariable("id") long id) {
        return serviceRetrievalUseCase.findById(id);
    }

    @GetMapping("/active")
    public List<Service> byOwner() {
        return serviceRetrievalUseCase.findAllActive();
    }
}
