package net.kravuar.business.dto;

import net.kravuar.accounts.model.Account;
import org.mapstruct.Mapper;

@Mapper
public interface DTOAccountMapper {
    AccountDTO toDto(Account account);
}
