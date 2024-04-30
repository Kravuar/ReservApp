package net.kravuar.accounts.web;

import lombok.RequiredArgsConstructor;
import net.kravuar.accounts.domain.commands.AccountCreationCommand;
import net.kravuar.accounts.model.Account;
import net.kravuar.accounts.ports.in.AccountManagementUseCase;
import net.kravuar.business.dto.AccountDTO;
import net.kravuar.business.dto.DTOAccountMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
class AccountManagementController {
    private final AccountManagementUseCase management;
    private final DTOAccountMapper mapper;

    @PostMapping("/create")
    AccountDTO create(@RequestBody AccountCreationCommand command) {
        Account account = management.createAccount(command);
        return mapper.toDto(account);
    }
}
