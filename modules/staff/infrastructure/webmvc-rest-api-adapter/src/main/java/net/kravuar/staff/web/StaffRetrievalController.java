package net.kravuar.staff.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.staff.ports.in.StaffRetrievalUseCase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retrieval")
@RequiredArgsConstructor
class StaffRetrievalController {
    private final StaffRetrievalUseCase staffRetrieval;

}
