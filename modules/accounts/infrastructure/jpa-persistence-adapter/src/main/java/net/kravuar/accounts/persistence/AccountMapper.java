package net.kravuar.accounts.persistence;

import net.kravuar.accounts.domain.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface AccountMapper {
    AccountModel toModel(Account account);
    Account toDomain(AccountModel accountModel);
}
