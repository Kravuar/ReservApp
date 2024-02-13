package net.kravuar.accounts.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.ports.in.AccountRetrievalUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/retrieval")
//@CrossOrigin(origins = "only the server can access it") TODO: idk how to do that
@RequiredArgsConstructor
class AccountRetrievalController {
    private final AccountRetrievalUseCase retrieval;
    private final AccountMapper mapper;

    @GetMapping("/by-id/{id}")
    AccountDTO findById(@PathVariable long id) {
        return mapper.toDto(retrieval.findById(id));
    }
}
