package net.kravuar.staff;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.domain.Service;
import net.kravuar.staff.ports.in.ServiceRetrievalUseCase;
import net.kravuar.staff.ports.out.ServiceRetrievalPort;

@AppComponent
@RequiredArgsConstructor
public class ServiceRetrievalFacade implements ServiceRetrievalUseCase {
    private final ServiceRetrievalPort serviceRetrievalPort;

    @Override
    public Service findById(long id) {
        return serviceRetrievalPort.findById(id);
    }
}
