package net.kravuar.accounts.web;

import net.kravuar.accounts.domain.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface AccountMapper {
    AccountDTO toDto(Account account);
}
