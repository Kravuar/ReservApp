package net.kravuar.schedule;

import lombok.RequiredArgsConstructor;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.model.Staff;
import net.kravuar.schedule.ports.in.StaffRetrievalUseCase;
import net.kravuar.schedule.ports.out.StaffRetrievalPort;

@AppComponent
@RequiredArgsConstructor
public class StaffRetrievalFacade implements StaffRetrievalUseCase {
    private final StaffRetrievalPort staffRetrievalPort;

    @Override
    public Staff findById(long staffId) {
        return staffRetrievalPort.findActiveById(staffId);
    }
}
